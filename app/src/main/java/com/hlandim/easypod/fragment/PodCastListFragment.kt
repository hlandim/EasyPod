package com.hlandim.easypod.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlandim.easypod.R
import com.hlandim.easypod.logic.PodCastListViewModel

/**
 * Created by hlandim on 08/11/17.
 */

class PodCastListFragment : Fragment() {

    var podCastListViewModel: PodCastListViewModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pod_cast_list, container, false)

        return view
    }


}