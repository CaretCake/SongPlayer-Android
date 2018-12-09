/*  Melissa Farrell
    This program pulls HTML from a hardcoded web URL which contains mp3 files and corresponding
    jpg files for each song. It parses the name and URL for each song from the HTML, stores it in
    a singleton list class, and populates a scrollable list of the songs. The user can click on any
    song in the list, which will pull up a fragment displaying the associated image for the song
    and start playing the song. Hitting the back button returns to the list while the song will
    continue to play until finished or the user chooses another song from the list.
 */

package c.melissa.songplayer

import android.content.*
import android.support.v4.app.*
import java.util.*


class MainActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        val songId = intent.getSerializableExtra(EXTRA_SONG_ID) as UUID
        return SongFragment.newInstance(songId)
    }

    companion object {

        private val EXTRA_SONG_ID = "c.melissa.fragment1.song_id"

        fun newIntent(packageContext: Context, songId: UUID): Intent {
            val intent = Intent(packageContext, MainActivity::class.java)
            intent.putExtra(EXTRA_SONG_ID, songId)
            return intent
        }
    }
}
