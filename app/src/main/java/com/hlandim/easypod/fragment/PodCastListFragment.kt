package com.hlandim.easypod.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.hlandim.easypod.R
import com.hlandim.easypod.activity.search.SearchActivity
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.logic.EpisodeListViewModel
import com.hlandim.easypod.logic.PodCastListViewModel
import kotlinx.android.synthetic.main.fragment_pod_cast_list.*

/**
 * Created by hlandim on 08/11/17.
 */

class PodCastListFragment : Fragment(), PodCastListAdapter.PodCastListListener {

    override fun onPodCastClicked(podCast: PodCast) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var podCastListViewModel: PodCastListViewModel? = null
    var episodeListViewModel: EpisodeListViewModel? = null
    var adapter: PodCastListAdapter? = null
    var isListViewMode = true

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pod_cast_list, container, false)



        episodeListViewModel = ViewModelProviders.of(this).get(EpisodeListViewModel::class.java)
        podCastListViewModel = ViewModelProviders.of(this).get(PodCastListViewModel::class.java)
        podCastListViewModel?.getPodCastList()?.observe(activity, Observer<MutableList<PodCast>> { list ->
            if (list != null) {
                adapter?.update(list.toList())
                episodeListViewModel?.getEpisode(list)
            }
        })

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        podcast_list.layoutManager = if (isListViewMode) GridLayoutManager(this.context, 4) else LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        item?.icon = ContextCompat.getDrawable(activity, R.drawable.view_grid)
        adapter?.notifyDataSetChanged()
        isListViewMode = !isListViewMode


        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurePodCastList()

        configureFabAddButtom()
    }

    private fun configureFabAddButtom() {
        fabAddPodCast.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configurePodCastList() {
        adapter = PodCastListAdapter(listOf(), this.activity, this)
        podcast_list.adapter = adapter
        val layoutManager = GridLayoutManager(this.context, 4)
        podcast_list.layoutManager = layoutManager
    }


    override fun onResume() {
        super.onResume()
        podCastListViewModel?.listSubscribedPodCasts()
    }


}