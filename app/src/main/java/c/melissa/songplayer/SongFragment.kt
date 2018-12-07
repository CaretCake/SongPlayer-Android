/*  Melissa Farrell
    SongFragment handles the fragment for each Song. This handles the view for when you
    click on a category in the list and pull up the fragment_song layout. The corresponding
    image display and playing of the actual song is handled here.
 */

package c.melissa.songplayer

import android.content.Context
import android.os.*
import android.support.v4.app.*
import android.view.*
import java.util.*

class SongFragment : Fragment() {
    private var mSong: Song? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val songID = arguments!!.getSerializable(ARG_SONG_ID) as UUID
        mSong = SongLab.get(getContext() as Context).getSong(songID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_song, container, false)

        // TODO: Create and set up view when clicking on a song in list, play music here

        return view
    }

    companion object {

        private val ARG_SONG_ID = "song_id"

        fun newInstance(songID: UUID): SongFragment {
            val args = Bundle()
            args.putSerializable(ARG_SONG_ID, songID)
            val fragment = SongFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
