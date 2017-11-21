package com.hlandim.easypod.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.main.MainFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    companion object {
        val FRAGMENT_TAG = "fragment_tag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureDrawer()

        startFragment()

    }

    private fun startFragment() {
        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment == null) {
            fragment = MainFragment()
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

    private fun configureDrawer() {
        setSupportActionBar(my_toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, my_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
