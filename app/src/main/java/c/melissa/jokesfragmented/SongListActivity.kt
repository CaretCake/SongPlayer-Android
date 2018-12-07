package c.melissa.jokesfragmented

import android.support.v4.app.Fragment

class SongListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return SongListFragment()
    }
}
