/*  Melissa Farrell
    SongListFragment handles the creation of the fragments, the creation of each one in the
    list and correctly assigning text from each Song to each fragment, and the recycling of
    the fragments as scrolling occurs.
 */

package c.melissa.songplayer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class SongListFragment : Fragment() {

    private var mSongRecyclerView: RecyclerView? = null
    private var mAdapter: SongAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_song_list, container, false)
        mSongRecyclerView = view.findViewById(R.id.song_recycler_view)
        mSongRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val songLab = SongLab.get(getContext() as Context)
        val songs = songLab.songs
        if (mAdapter == null) {
            mAdapter = SongAdapter(songs)
            mSongRecyclerView!!.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    private inner class SongHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var mSong: Song? = null
        lateinit var mSongNameTextView: TextView

        init {
            itemView.setOnClickListener(this)
            mSongNameTextView = itemView.findViewById(R.id.list_item_song_name_text_view) as TextView

        }

        internal fun bindSong(song: Song) {
            mSong = song
            mSongNameTextView.text = mSong!!.songName
        }

        override fun onClick(v: View) {
            val intent = MainActivity.newIntent(getContext() as Context, mSong!!.id)
            startActivity(intent)
        }
    }

    private inner class SongAdapter internal constructor(private val mSongs: List<Song>) : RecyclerView.Adapter<SongHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
            val layoutInflater = LayoutInflater.from(activity)
            val view = layoutInflater.inflate(R.layout.list_item_song, parent, false)
            return SongHolder(view)
        }

        override fun onDetachedFromRecyclerView(rv: RecyclerView) {
            super.onDetachedFromRecyclerView(rv)
        }

        override fun onBindViewHolder(holder: SongHolder, position: Int) {
            val song = mSongs[position]
            holder.bindSong(song)
        }

        override fun getItemCount(): Int {
            return mSongs.size
        }
    }
}
