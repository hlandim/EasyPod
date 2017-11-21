package com.hlandim.easypod.fragment.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.main.adapter.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * Created by hugo.landim.santos on 21/11/2017.
 */
class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabFragments = listOf(PodCastListFragment(), PlayListFragment())
        val viewPagerAdapter = SimpleFragmentPagerAdapter(tabFragments, childFragmentManager)
        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)

    }
}