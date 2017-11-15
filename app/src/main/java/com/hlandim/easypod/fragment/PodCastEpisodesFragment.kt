package com.hlandim.easypod.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlandim.easypod.R
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

/**
 * Created by hlandim on 14/11/17.
 */
class PodCastEpisodesFragment : Fragment(), Callback {
    override fun onLoadFailed() {
        println("onLoadFailed")
    }

    override fun onPreload() {
        println("Preload")
    }

    override fun onLoaded(newArticles: MutableList<Article>?) {
        println(newArticles)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pod_cast_episodes, container, false)

        PkRSS.with(activity).load("https://jovemnerd.com.br/feed-nerdcast/").callback(this).async();

        return view
    }
}