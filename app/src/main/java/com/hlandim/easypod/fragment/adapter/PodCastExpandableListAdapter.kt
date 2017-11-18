package com.hlandim.easypod.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.bumptech.glide.Glide
import com.hlandim.easypod.R
import com.hlandim.easypod.domain.Episode
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.logic.EpisodeListViewModel
import kotlinx.android.synthetic.main.podcast_list_expandeble_child.view.*
import kotlinx.android.synthetic.main.podcast_list_expandeble_group.view.*

/**
 * Created by hlandim on 16/11/17.
 */
class PodCastExpandableListAdapter(var list: MutableList<EpisodeListViewModel.PodCastEpisodes>, val context: Context) : BaseExpandableListAdapter() {

    override fun getGroupView(position: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        val podCast: PodCast = list[position].podCast
        val view: View = if (convertView == null) {
            val layoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(R.layout.podcast_list_expandeble_group, parent, false)
        } else {
            convertView
        }

        if (isExpanded) {
            view.img_arrow.setImageResource(R.drawable.arrow_close)
        } else {
            view.img_arrow.setImageResource(R.drawable.arrow_open)
        }

        Glide.with(context).load(podCast.imgThumbUrl).into(view.img_logo)
        view.tv_title.text = podCast.title

        return view

    }

    override fun getChildView(positionGroup: Int, positionChild: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {

        val view: View = if (convertView == null) {
            val layoutInflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            layoutInflater.inflate(R.layout.podcast_list_expandeble_child, parent, false)
        } else {
            convertView
        }
        if (list[positionGroup].episodes.isEmpty()) {
            view.layout_loading.visibility = View.VISIBLE
        } else {
            val episode: Episode = list[positionGroup].episodes[positionChild]
            view.layout_loading.visibility = View.GONE
            view.tv_episode_title.text = episode.title
        }
        return view
    }

    fun update(newList: MutableList<EpisodeListViewModel.PodCastEpisodes>) {
        list = newList
        notifyDataSetChanged()
    }

    fun update(pcEps: EpisodeListViewModel.PodCastEpisodes) {
        val index = list.indexOf(pcEps)
        if (index != -1) {
            list.removeAt(index)
            list.add(index, pcEps)
            notifyDataSetChanged()
        }
    }

    override fun hasStableIds(): Boolean = false

    override fun isChildSelectable(p0: Int, p1: Int): Boolean = true

    override fun getGroup(p0: Int): Any = list[p0].podCast

    override fun getChildrenCount(p0: Int): Int {
        if (list[p0].episodes.isEmpty()) {
            return 1
        }
        return list[p0].episodes.size
    }

    override fun getChild(p0: Int, p1: Int): Any = list[p0].episodes[p1]

    override fun getGroupId(p0: Int): Long = list[p0].podCast.id

    override fun getChildId(p0: Int, p1: Int): Long {

        if (list[p0].episodes.isEmpty()) {
            return -1
        }
        return list[p0].episodes[p1].id
    }

    override fun getGroupCount(): Int = list.size
}