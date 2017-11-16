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
class EpisodeListViewModel(application: Application) : AndroidViewModel(application) {

    private var podCastEpisodes: MutableLiveData<MutableSet<PodCastEpisodes>> = MutableLiveData()

    fun getEpisode(podCasts: List<PodCast>) {
        podCasts.forEach({ podCast ->
            PkRSS.with(getApplication()).load(podCast.feedUrl).callback(object : Callback {
                override fun onLoadFailed() {
                    println("PkRSS - onLoadFailed")

                }

                override fun onPreload() {
                    println("PKRSS - onPreload")
                }

                override fun onLoaded(newArticles: MutableList<Article>?) {
                    println(newArticles?.size)
                    val episodes: List<Episode>? = newArticles?.take(25)?.map { article ->
                        Episode(id = 0,
                                idApi = article.id.toLong(),
                                podCastId = podCast.id,
                                title = article.title)
                    }
                    if (episodes != null) {
                        episodes.forEach { ep ->
                            DataBaseUtils.getAppDataBase(getApplication()).episodeDao().insert(ep)
                        }
                        podCastEpisodes.value?.add(PodCastEpisodes(podCast, episodes))
                    }
                }
            }).async()
        })

    }

    data class PodCastEpisodes(val podCast: PodCast,
                               val episodes: List<Episode>)
}


