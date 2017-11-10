package com.hlandim.easypod.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.hlandim.easypod.logic.PodCastListViewModel

/**
 * Created by hlandim on 08/11/17.
 */

class PodCastListFragment : Fragment() {

    var podCastListViewModel: PodCastListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProviders.of(this).get(PodCastListViewModel::class.java).search("Jovem nerd")
    }



}