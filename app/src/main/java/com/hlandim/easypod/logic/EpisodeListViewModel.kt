package com.hlandim.easypod.logic

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.hlandim.easypod.dao.DataBaseUtils
import com.hlandim.easypod.domain.Episode
import com.hlandim.easypod.domain.PodCast
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

/**
 * Created by hlandim on 15/11/17.
 */
class EpisodeListViewModel(application: Application) : AndroidViewModel(application), Callback {

    var podCastEpisodes: MutableLiveData<MutableSet<PodCastEpisodes>> = MutableLiveData()
    private var podCastEpisodesTmp: MutableSet<PodCastEpisodes> = mutableSetOf()
    private var indexFetched: Int = 0
    private var podCastList: List<PodCast> = listOf()


    fun fetchEpisodes(podCasts: List<PodCast>) {
        indexFetched = 0
        podCastList = podCasts
        getEpisodes(podCasts[indexFetched])

    }

    private fun getEpisodes(podCast: PodCast) {

        val episodes = DataBaseUtils.getAppDataBase(getApplication()).episodeDao().getByPodCast(podCastId = podCast.id)
        if (episodes.isNotEmpty()) {
            podCastEpisodesTmp.add(PodCastEpisodes(podCast, episodes))
            checkNextPodCastEpisodes()
        } else {
            PkRSS.with(getApplication()).load(podCast.feedUrl).callback(this).async()
        }
    }

    override fun onLoadFailed() {
        println("PkRSS - onLoadFailed")

    }

    override fun onPreload() {
        println("PKRSS - onPreload")
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        println("PKRSS - " + newArticles?.size)
        val podCast = podCastList[indexFetched]
        val episodes: List<Episode>? = newArticles?.take(5)?.map { article ->
            Episode(id = 0,
                    idApi = article.id.toLong(),
                    podCastId = podCast.id,
                    title = article.title)
        }
        if (episodes != null) {
            episodes.forEach { ep ->
                DataBaseUtils.getAppDataBase(getApplication()).episodeDao().insert(ep)
            }
            podCastEpisodesTmp.add(PodCastEpisodes(podCast, episodes))
        } else {
            podCastEpisodesTmp.add(PodCastEpisodes(podCast, listOf()))

        }
        checkNextPodCastEpisodes()
    }

    private fun checkNextPodCastEpisodes() {
        indexFetched++
        if (indexFetched <= podCastList.size - 1) {
            getEpisodes(podCastList[indexFetched])
        } else {
            podCastEpisodes.value = podCastEpisodesTmp
        }
    }

    data class PodCastEpisodes(val podCast: PodCast,
                               val episodes: List<Episode>)
}


