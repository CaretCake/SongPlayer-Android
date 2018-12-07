package c.melissa.songplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

abstract class SingleFragmentActivity : FragmentActivity() {
    protected abstract fun createFragment(): Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
}
