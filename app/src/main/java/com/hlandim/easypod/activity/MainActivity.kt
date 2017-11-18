package com.hlandim.easypod.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.PodCastListFragment
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    companion object {
        val FRAGMENT_TAG = "fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(my_toolbar)

        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment == null) {
            fragment = PodCastListFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragments_container, fragment, FRAGMENT_TAG)
                    .commit()
        } else {
            supportFragmentManager
                    .beginTransaction()
                    .show(fragment)
                    .commit()
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_activity_menu, menu)
//        return true
//    }

    /* override fun onOptionsItemSelected(item: MenuItem?): Boolean {
         val actualFragment
                 : Fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
         return actualFragment.onOptionsItemSelected(item)
     }*/


}
