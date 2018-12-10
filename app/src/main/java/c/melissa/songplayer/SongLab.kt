/*  Melissa Farrell
    This is a singleton that creates the list of songs for use by the app from the hardcoded
    website link. If one already exists, it returns the created list. If the list has not yet
    been created, it will pull the HTML from the hardcoded URL and parse it into a song name and
    corresponding URL, then store that as a Song in the list.
 */

package c.melissa.songplayer

import android.content.Context
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.security.AccessController.getContext

import java.util.ArrayList
import java.util.UUID

class SongLab private constructor(context: Context) {
    private val mSongs: MutableList<Song>
    internal var webthread: Boolean = false
    internal var htmlText: String = ""
    var urlSongMap:MutableMap<String, String> = mutableMapOf<String, String>()

    val songs: List<Song>
        get() = mSongs

    fun getSong(id: UUID): Song? {
        for (song in mSongs)
            if (song.id == id) {
                return song
            }
        return null
    }

    init { //Constructor is private; so no outside force can make a new one
        mSongs = ArrayList()

        webthread = false
        RetrieveFeedTask().execute(null as Void?)
        while (!webthread);

        parseUrls()


        for (song in urlSongMap.keys) {
            val newSong = Song()
            newSong.songName = song
            newSong.songLink = urlSongMap.get(song)
            mSongs.add(newSong)
        }
    }

    companion object {

        private var sSongLab: SongLab? = null

        operator fun get(context: Context): SongLab {
            if (sSongLab == null) sSongLab = SongLab(context)
            return sSongLab as SongLab
        }
    }

    /*
        Parses the HTML into the name and url (minus the .filetype) for each song on the page and
        stores it into a map of song name -> url.
     */
    fun parseUrls () {
        val regexPattern = Regex("<A HREF=\"(\\/(\\d|\\w|\\/|%)*)\\..{3}\">((\\s|\\d|\\w)*)\\.(.{3})<\\/A>")
        val foundMatches = regexPattern.findAll(htmlText)

        foundMatches.forEach { match ->
            if (!urlSongMap.containsKey(match.groups[3]!!.value)) {
                urlSongMap[match.groups[3]!!.value] = match.groups[1]!!.value
            }
        }
    }

    /*
        Gets the HTML from the given page.
     */
    internal inner class RetrieveFeedTask : AsyncTask<Void, Void, Void>() {

        private val exception: Exception? = null

        override fun doInBackground(vararg urls: Void): Void? {
            try {
                val obj = URL("http://philos.nmu.edu/weirdal/")
                val con = obj.openConnection() as HttpURLConnection
                con.requestMethod = "GET"
                val responseCode = con.responseCode
                println("GET Response Code :: $responseCode")
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    var website = BufferedReader(InputStreamReader(con.inputStream))
                    while (true) {
                        val c = website.read()
                        if (c == -1) break
                        print(c.toChar())
                        htmlText += c.toChar()
                    }
                    website.close()

                } else {
                    println("GET request failed")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            webthread = true
            return null
        }
    }
}