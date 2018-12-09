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
        val res = context.resources

        webthread = false
        RetrieveFeedTask().execute(null as Void?)
        while (!webthread);
        println("Wow!  What a great website!")

        parseUrls()


        // TO DO: change to get list from website content

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


    fun parseUrls () {
        val regexPattern = Regex("<A HREF=\"(\\/(\\d|\\w|\\/|%)*)\\..{3}\">((\\s|\\d|\\w)*)\\.(.{3})<\\/A>")
        val foundMatches = regexPattern.findAll(htmlText)

        foundMatches.forEach { match ->
            /*println(match)
            println(match.groups[0]) // Full match
            println(match.groups[1]) // URL
            println(match.groups[3]) // Name
            println(match.groups[5]) // File type*/
            if (!urlSongMap.containsKey(match.groups[3]!!.value)) {
                urlSongMap[match.groups[3]!!.value] = match.groups[1]!!.value
                //println("map at: " + match.groups[3]!!.value + " is now: " + match.groups[1]!!.value)
            }
        }
    }

    internal inner class RetrieveFeedTask : AsyncTask<Void, Void, Void>() {

        private val exception: Exception? = null

        override fun doInBackground(vararg urls: Void): Void? {
            //val context = getContext() as Context
            println("About to begin")
            try {
                val obj = URL("http://philos.nmu.edu/weirdal/")
                val con = obj.openConnection() as HttpURLConnection
                println("Connected")
                con.requestMethod = "GET"
                val responseCode = con.responseCode
                println("GET Response Code :: $responseCode")
                if (responseCode == HttpURLConnection.HTTP_OK) { // success
                    var website = BufferedReader(InputStreamReader(con.inputStream))
                    /*val tmpdir = context.cacheDir.toString()
                    val out = PrintWriter("$tmpdir/license.txt")
                    while (true) {
                        val c = `in`.read()
                        if (c == -1) break
                        out.print(c.toChar())
                    }
                    `in`.close()

                    out.close()*/
                    //`in` = BufferedReader(InputStreamReader(FileInputStream("$tmpdir/license.txt")))
                    while (true) {
                        val c = website.read()
                        if (c == -1) break
                        print(c.toChar())
                        htmlText += c.toChar()
                    }
                    website.close()

                } else {
                    println("GET request not worked")
                }
            } catch (e: Exception) {
                println("Yeah")
                e.printStackTrace()
            }

            webthread = true
            return null
        }
    }
}