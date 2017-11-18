package com.hlandim.easypod.logic

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.hlandim.easypod.dao.DataBaseUtils
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.logic.web.itunes.ItunesSearchApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hlandim on 08/11/17.
 */
class PodCastListViewModel(application: Application) : AndroidViewModel(application) {

    private var podCastList: MutableLiveData<MutableList<PodCast>> = MutableLiveData()
    private var listTmp: MutableList<PodCast> = mutableListOf()
    private var queryCache: String = ""

    fun getPodCastList(): MutableLiveData<MutableList<PodCast>> = podCastList

    fun listSubscribedPodCasts() {
        podCastList.value = getDao().getAll() as MutableList<PodCast>
    }

    fun delete(podCast: PodCast) {
        val index = podCastList.value?.indexOf(podCast) as Int
        if (index != -1) {
            getDao().delete(podCast)
            podCastList.value?.removeAt(index)
            podCastList.value = podCastList.value
        }
    }

    fun search(value: String) {

        if (!value.isBlank()) {

            if (!queryCache.isBlank() && value.startsWith(queryCache)) {
                val tempList: List<PodCast>? = podCastList.value?.filter { item -> item.title.startsWith(value) }
                podCastList.value = tempList as MutableList<PodCast>?
                queryCache = value
            } else {
                listTmp.clear()
                ItunesSearchApi
                        .instance
                        .search(value)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ result ->
                            val podCastDb: PodCast? = getDao().getByApiId(result.idApi)
                            result.signed = podCastDb != null
                            listTmp.add(result)

                        }, { error ->
                            error.printStackTrace()
                        }, {
                            queryCache = value
                            podCastList.value = listTmp
                        })
            }
        }
    }

    private fun getDao() = DataBaseUtils.getAppDataBase(getApplication()).podCastDao()

}