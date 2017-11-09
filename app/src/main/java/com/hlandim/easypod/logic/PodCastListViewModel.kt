package com.hlandim.easypod.logic

import android.arch.lifecycle.ViewModel
import com.hlandim.easypod.domain.PodCast

/**
 * Created by hlandim on 08/11/17.
 */
class PodCastListViewModel : ViewModel() {

    private val podCastList: MutableList<PodCast> = mutableListOf()

    fun addPodcast(podCast: PodCast) {
        podCastList.add(podCast)
    }

}