package com.hlandim.easypod.activity

import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.PodCastListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction().add(R.id.fragments_container, PodCastListFragment()).commit()
    }
}
