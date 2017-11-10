package com.hlandim.easypod.logic

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.hlandim.easypod.config.RetrofitInitializer
import com.hlandim.easypod.domain.PodCast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hlandim on 08/11/17.
 */
class PodCastListViewModel : ViewModel() {

    private var podCastList: MutableLiveData<List<PodCast>>? = null

    fun getPodCastList(): MutableLiveData<List<PodCast>> {
        if (podCastList == null) {
            podCastList = MutableLiveData()
        }
        return podCastList!!
    }

    fun search(value: String) {
        RetrofitInitializer()
                .podCastService()
                .search(value, "583b42eadd74a7d5f101e47f1961acc8160f317817f42142742035cc94022a09")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    println(result.results_per_page)
                }, { error ->
                    error.printStackTrace()
                })
        //println(response)
    }

    override fun onCleared() {
        super.onCleared()
    }

}