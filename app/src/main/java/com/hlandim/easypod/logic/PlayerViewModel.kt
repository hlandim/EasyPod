package com.hlandim.easypod.logic

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.hlandim.easypod.domain.Episode
import com.hlandim.easypod.domain.PodCast

/**
 * Created by hlandim on 22/11/17.
 */
class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    var playList: MutableLiveData<MutableList<PlayerListItem>> = MutableLiveData()

    fun addEpisode(playerListItem: PlayerListItem) {
        playList.value?.add(playerListItem)
    }

    data class PlayerListItem(val podCast: PodCast, val episode: Episode)

}