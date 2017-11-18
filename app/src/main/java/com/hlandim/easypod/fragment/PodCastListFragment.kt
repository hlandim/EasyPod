package com.hlandim.easypod.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.hlandim.easypod.R
import com.hlandim.easypod.activity.search.SearchActivity
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.fragment.adapter.PodCastExpandableListAdapter
import com.hlandim.easypod.fragment.adapter.PodCastListAdapter
import com.hlandim.easypod.logic.EpisodeListViewModel
import com.hlandim.easypod.logic.PodCastListViewModel
import kotlinx.android.synthetic.main.fragment_pod_cast_list.*

/**
 * Created by hlandim on 08/11/17.
 */

class PodCastListFragment : Fragment(), PodCastListAdapter.PodCastListListener {


    private var podCastListViewModel: PodCastListViewModel? = null
    private var episodeListViewModel: EpisodeListViewModel? = null
    private var adapterList: PodCastListAdapter? = null
    private var adapterExpandable: PodCastExpandableListAdapter? = null
    private var isListViewMode = true

    companion object {
        val TAG: String = PodCastListFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pod_cast_list, container, false)



        episodeListViewModel = ViewModelProviders.of(this).get(EpisodeListViewModel::class.java)
        podCastListViewModel = ViewModelProviders.of(this).get(PodCastListViewModel::class.java)
        podCastListViewModel?.getPodCastList()?.observe(activity, Observer<MutableList<PodCast>> { list ->
            if (list != null && list.isNotEmpty()) {

                adapterList?.update(list.map { pc -> PodCastListAdapter.PodCastSync(pc, true) })

                val expandableList = list.map { podCast -> EpisodeListViewModel.PodCastEpisodes(podCast, listOf()) }.toMutableList()
                adapterExpandable = PodCastExpandableListAdapter(expandableList, activity)
                podcast_expandable_list.setAdapter(adapterExpandable)
                episodeListViewModel?.fetchEpisodes(list)
            }
        })
        episodeListViewModel?.podCastEpisodes?.observe(activity, Observer<EpisodeListViewModel.PodCastEpisodes> { pcEps ->
            if (pcEps != null) {
                adapterExpandable?.update(pcEps)
                adapterList?.finishSync(pcEps.podCast)
            }
        })
        setHasOptionsMenu(true)
        retainInstance = true
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        activity.menuInflater.inflate(R.menu.podcast_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_change_list_layout -> {
                if (isListViewMode) {
                    item.icon = ContextCompat.getDrawable(activity, R.drawable.view_grid)
                    podcast_list_grid.visibility = View.GONE
                    podcast_expandable_list.visibility = View.VISIBLE
                } else {
                    podcast_list_grid.visibility = View.VISIBLE
                    podcast_expandable_list.visibility = View.GONE
                    item.icon = ContextCompat.getDrawable(activity, R.drawable.view_list)
                }

                isListViewMode = !isListViewMode

            }
            R.id.action_podcasts_sync -> {
                if (episodeListViewModel != null && episodeListViewModel!!.isUpdating) {
                    Toast.makeText(activity, "Already updating...", Toast.LENGTH_LONG).show()
                } else if (adapterExpandable != null && adapterExpandable?.list != null) {
                    Log.i(TAG, "Forcing sync!")
                    val newExpandableList: MutableList<EpisodeListViewModel.PodCastEpisodes> = mutableListOf()
                    val list = adapterExpandable!!.list.map { pcEps -> pcEps.podCast }
                    list.forEach { itemList -> newExpandableList.add(EpisodeListViewModel.PodCastEpisodes(itemList, listOf())) }
                    adapterExpandable!!.update(newExpandableList)
                    episodeListViewModel?.fetchEpisodes(list, true)
                    adapterList?.update(list.map { pc -> PodCastListAdapter.PodCastSync(pc, true) })
                }
            }
        }



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
        adapterList = PodCastListAdapter(listOf(), this.activity, this)
        podcast_list_grid.adapter = adapterList
        val layoutManager = GridLayoutManager(this.context, 4)
        podcast_list_grid.layoutManager = layoutManager
        if (isListViewMode) {
            podcast_list_grid.visibility = View.VISIBLE
            podcast_expandable_list.visibility = View.GONE
        } else {
            podcast_list_grid.visibility = View.GONE
            podcast_expandable_list.visibility = View.VISIBLE
        }
    }


    override fun onResume() {
        super.onResume()
        podCastListViewModel?.listSubscribedPodCasts()
    }

    override fun onPodCastClicked(podCast: PodCast) {
        Toast.makeText(activity, "Wait for it!!", Toast.LENGTH_LONG).show()
    }
}