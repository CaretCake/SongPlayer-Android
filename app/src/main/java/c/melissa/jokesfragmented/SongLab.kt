/*  Melissa Farrell
    This is a singleton that creates the list of songs for use by the app from the hardcoded
    website link. If one already exists, it returns the created list. If the list has not yet
    been created, it will pull the HTML from the hardcoded URL and parse it into a song name and
    corresponding URL, then store that as a Song in the list.
 */

package c.melissa.jokesfragmented

import android.content.Context

import java.util.ArrayList
import java.util.UUID

class SongLab private constructor(context: Context) {
    private val mSongs: MutableList<Song>

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
        // TODO: change to get list from website content

        for (i in 0..29) {
            val newSong = Song()
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
}