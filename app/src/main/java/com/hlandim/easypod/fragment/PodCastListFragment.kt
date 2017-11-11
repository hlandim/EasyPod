package com.hlandim.easypod.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlandim.easypod.R
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.logic.PodCastListViewModel
import kotlinx.android.synthetic.main.fragment_pod_cast_list.*

/**
 * Created by hlandim on 08/11/17.
 */

class PodCastListFragment : Fragment() {

    var podCastListViewModel: PodCastListViewModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pod_cast_list, container, false)

        podCastListViewModel = ViewModelProviders.of(this).get(PodCastListViewModel::class.java)
        podCastListViewModel?.getPodCastList()?.observe(activity, Observer<MutableList<PodCast>> { list ->
            list?.forEach { podCast ->
                textPodCasts.text = textPodCasts.text.toString() + "\n" + podCast.title
            }
        })

        podCastListViewModel?.search("nerd")
        return view
    }


}