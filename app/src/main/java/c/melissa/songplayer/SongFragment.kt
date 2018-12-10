/*  Melissa Farrell
    SongFragment handles the fragment for each Song. This handles the view for when you
    click on a category in the list and pull up the fragment_song layout. The corresponding
    image display and playing of the actual song is handled here.
 */

package c.melissa.songplayer

import android.content.Context
import android.graphics.Bitmap
import android.os.*
import android.support.v4.app.*
import android.view.*
import android.widget.TextView
import java.util.*
import java.util.concurrent.TimeUnit
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView


class SongFragment : Fragment() {
    private var mSong: Song? = null
    internal var DONE: Boolean = false
    lateinit var songNameTextView: TextView
    lateinit var songImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val songID = arguments!!.getSerializable(ARG_SONG_ID) as UUID
        mSong = SongLab.get(getContext() as Context).getSong(songID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_song, container, false)

        songNameTextView = view.findViewById(R.id.song_name) as TextView
        songImageView = view.findViewById(R.id.song_image) as ImageView
        songNameTextView.text = mSong!!.songName

        DownloadImageTask(songImageView).execute("http://philos.nmu.edu"+mSong!!.songLink+".jpg")


        return view
    }

    /*
        Starts playing the selected song by pulling from given url.
     */
    override fun onResume() {
        super.onResume()
        DONE = false
        try {
            mp.reset()
            mp.setDataSource("http://philos.nmu.edu"+mSong!!.songLink+".mp3")
            mp.prepare()
            mp.start()
            mp.start()
            try {
                TimeUnit.SECONDS.sleep(10)
            } catch (e: Exception) {

            }

            mp.pause()
            try {
                TimeUnit.SECONDS.sleep(5)
            } catch (e: Exception) {

            }

            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

    /*
        Downloads the song's associated image from the given url.
     */
    private inner class DownloadImageTask(internal var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urldisplay = urls[0]
            var mIcon11: Bitmap? = null
            try {
                val `in` = java.net.URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
            }

            return mIcon11
        }

        override fun onPostExecute(result: Bitmap) {
            bmImage.setImageBitmap(result)
        }
    }
}
