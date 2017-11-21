package com.hlandim.easypod.fragment.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hlandim.easypod.fragment.main.MainTabLayoutFragment

/**
 * Created by hugo.landim.santos on 21/11/2017.
 */
class SimpleFragmentPagerAdapter(private val fragments: List<MainTabLayoutFragment>, fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence {
        return fragments[position].getTabName()
    }
}