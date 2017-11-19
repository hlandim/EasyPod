package com.hlandim.easypod.logic

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.hlandim.easypod.dao.DataBaseUtils
import com.hlandim.easypod.dao.EpisodeDao
import com.hlandim.easypod.domain.Episode
import com.hlandim.easypod.domain.PodCast
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

/**
 * Created by hlandim on 15/11/17.
 */
class EpisodeListViewModel(application: Application) : AndroidViewModel(application), Callback {

    var podCastEpisodes: MutableLiveData<PodCastEpisodes> = MutableLiveData()
    private var indexFetched: Int = 0
    private var podCastList: MutableList<PodCast> = mutableListOf()
    private var forceSync: Boolean = false
    var isUpdating: Boolean = false

    companion object {
        val TAG: String = EpisodeListViewModel::class.java.simpleName
    }

    fun deleteAllFromPodCast(podCast: PodCast) {
        getDao().deleteAllFromPodCast(podCast.id)
    }

    fun fetchEpisodes(podCasts: List<PodCast>, forceSync: Boolean) {
        this.forceSync = forceSync
        fetchEpisodes(podCasts)
    }

    fun fetchEpisodes(podCasts: List<PodCast>) {
        if (isUpdating) {
            podCastList.addAll(podCasts)
        } else {
            podCastList = podCasts.toMutableList()
            indexFetched = 0
            isUpdating = true
            fetchEpisodes(podCastList[indexFetched])
        }
    }

    private fun fetchEpisodes(podCast: PodCast) {
        if (forceSync) {
            getFeed(podCast)
        } else {
            val episodes = DataBaseUtils.getAppDataBase(getApplication()).episodeDao().getByPodCast(podCastId = podCast.id)
            Log.d(TAG, "${podCast.title} - episodes from database: ${episodes.size}")
            if (episodes.isNotEmpty()) {
                val pcEp = PodCastEpisodes(podCast, episodes)
                podCastEpisodes.value = pcEp
                checkNextPodCastEpisodes()
            } else {
                getFeed(podCast)
            }
        }

    }

    private fun getFeed(podCast: PodCast) {
        Log.d(TAG, "Fetching episodes from ${podCast.title} - ${podCast.feedUrl}")
        PkRSS.with(getApplication()).load(podCast.feedUrl).callback(this).async()
    }

    override fun onLoadFailed() {
        Log.d(TAG, "PkRSS - onLoadFailed")

    }

    override fun onPreload() {
        Log.d(TAG, "PKRSS - onPreload")
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        val podCast = podCastList[indexFetched]
        val episodes: List<Episode>? = newArticles?.take(5)
                ?.filter { article -> article.enclosure != null }
                ?.map { article ->
                    Episode(id = 0,
                            idApi = article.id.toLong(),
                            podCastId = podCast.id,
                            title = article.title,
                            description = article.description,
                            mimeType = article.enclosure.mimeType,
                            url = article.enclosure.url)
                }

        val pcEp = if (episodes != null) {
            episodes.forEach { ep ->
                val epDao = getDao()
                val epDb = epDao.getByTitle(ep.title)
                if (epDb != null) {
                    ep.id = epDb.id
                }
                epDao.insert(ep)
            }
            PodCastEpisodes(podCast, episodes)
        } else {
            PodCastEpisodes(podCast, listOf())
        }
        podCastEpisodes.value = pcEp
        checkNextPodCastEpisodes()
    }

    private fun getDao(): EpisodeDao = DataBaseUtils.getAppDataBase(getApplication()).episodeDao()

    private fun checkNextPodCastEpisodes() {
        indexFetched++
        if (isSyncFinished()) {
            isUpdating = false
            forceSync = false
        } else {
            fetchEpisodes(podCastList[indexFetched])
        }
    }

    private fun isSyncFinished() = indexFetched > podCastList.size - 1

    data class PodCastEpisodes(val podCast: PodCast,
                               val episodes: List<Episode>) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val that = other as PodCastEpisodes?
            return podCast.id == that?.podCast?.id
        }

        override fun hashCode(): Int = podCast.id.hashCode()
    }
}


