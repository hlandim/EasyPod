package com.hlandim.easypod.fragment.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.Toast
import com.hlandim.easypod.R
import com.hlandim.easypod.activity.search.SearchActivity
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.fragment.adapter.EpisodesListAdapter
import com.hlandim.easypod.fragment.adapter.PodCastListAdapter
import com.hlandim.easypod.fragment.custom.CenterLayoutManager
import com.hlandim.easypod.fragment.custom.SpacesItemDecoration
import com.hlandim.easypod.logic.EpisodeListViewModel
import com.hlandim.easypod.logic.PodCastListViewModel
import kotlinx.android.synthetic.main.fragment_pod_cast_list.*


/**
 * Created by hlandim on 08/11/17.
 */

class PodCastListFragment : MainTabLayoutFragment(), PodCastListAdapter.PodCastListListener {

    private var podCastListViewModel: PodCastListViewModel? = null
    private var episodeListViewModel: EpisodeListViewModel? = null
    private var adapterPodCastList: PodCastListAdapter? = null
    private var adapterEpisodesList: EpisodesListAdapter? = null
    private var isListViewMode = true
    private var selectedPodCast: PodCast? = null

    companion object {
        val TAG: String = PodCastListFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pod_cast_list, container, false)

        episodeListViewModel = ViewModelProviders.of(this).get(EpisodeListViewModel::class.java)
        podCastListViewModel = ViewModelProviders.of(this).get(PodCastListViewModel::class.java)
        podCastListViewModel?.getPodCastList()?.observe(activity, Observer<MutableList<PodCast>> { list ->
            if (list != null && list.isNotEmpty()) {

                adapterPodCastList?.update(list.map { pc -> PodCastListAdapter.PodCastSync(pc, true) })

                episodeListViewModel?.fetchEpisodes(list)

                onPodCastClicked(list[0], 0)
            }
        })

        episodeListViewModel?.podCastEpisodes?.observe(activity, Observer<EpisodeListViewModel.PodCastEpisodes> { pcEps ->
            if (pcEps != null) {
                //Updating episodes list
                if (selectedPodCast != null && selectedPodCast!! == pcEps.podCast) {
                    adapterEpisodesList?.update(pcEps.episodes)
                    podcast_title.text = pcEps.podCast.title
                }
                adapterPodCastList?.finishSync(pcEps.podCast)
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
                } else {
                    item.icon = ContextCompat.getDrawable(activity, R.drawable.view_list)
                }
                toggleListLayout()

            }
            R.id.action_podcasts_sync -> {
                if (episodeListViewModel != null && episodeListViewModel!!.isUpdating) {
                    Toast.makeText(activity, "Already updating...", Toast.LENGTH_LONG).show()
                } else {
                    Log.i(TAG, "Forcing sync!")
                    val list = adapterPodCastList!!.list.map { podCastSync -> podCastSync.podCast }
                    episodeListViewModel?.fetchEpisodes(list, true)
                    adapterPodCastList?.update(list.map { pc -> PodCastListAdapter.PodCastSync(pc, true) })
                }
            }
        }



        return super.onOptionsItemSelected(item)
    }

    private fun toggleListLayout() {
        if (isListViewMode) {

            //Hiding episodes list
            container_episodes.visibility = View.GONE

            //Configuring Grid
            val layoutManager = GridLayoutManager(activity, calculateNoOfColumns(activity))
            podcast_list.layoutManager = layoutManager

            //Setting constraint in recycleView
            val constraintSet = ConstraintSet()
            constraintSet.clone(constraint_root)
            constraintSet.connect(podcast_list.id, ConstraintSet.TOP, fabAddPodCast.id, ConstraintSet.BOTTOM)
            constraintSet.applyTo(constraint_root)
            podcast_list.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
            podcast_list.addItemDecoration(SpacesItemDecoration(3))

        } else {
            container_episodes.visibility = View.VISIBLE
            val layoutManager = CenterLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            podcast_list.layoutManager = layoutManager
        }

        isListViewMode = !isListViewMode
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurePodCastList()
        configureFabAddButton()
    }

    private fun configureFabAddButton() {
        fabAddPodCast.setOnClickListener {
            val intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configurePodCastList() {
        adapterPodCastList = PodCastListAdapter(listOf(), this.activity, this)
        podcast_list.adapter = adapterPodCastList
        var layoutManager = CenterLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        podcast_list.layoutManager = layoutManager

        toggleListLayout()

        layoutManager = CenterLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        episodes_list.layoutManager = layoutManager

        adapterEpisodesList = EpisodesListAdapter(listOf(), activity)
        episodes_list.adapter = adapterEpisodesList
    }


    override fun onResume() {
        super.onResume()
        podCastListViewModel?.listSubscribedPodCasts()
    }

    override fun onPodCastClicked(podCast: PodCast, position: Int) {
        selectedPodCast = podCast
        episodeListViewModel?.fetchEpisodes(listOf(podCast))
        podcast_list.smoothScrollToPosition(position)

    }

    override fun onPodCastRemoveClicked(podCast: PodCast) {
        episodeListViewModel?.deleteAllFromPodCast(podCast)
        podCastListViewModel?.delete(podCast)
    }

    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val sizeOfGridItem = 80
        return (dpWidth / sizeOfGridItem).toInt()
    }

    override fun getTabName(): String = "PodCast"
}