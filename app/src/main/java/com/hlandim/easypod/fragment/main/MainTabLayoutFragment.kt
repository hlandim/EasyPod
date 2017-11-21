package com.hlandim.easypod.fragment.main

import android.support.v4.app.Fragment

/**
 * Created by hugo.landim.santos on 21/11/2017.
 */
abstract class MainTabLayoutFragment : Fragment() {
    abstract fun getTabName(): String
}