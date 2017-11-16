package com.hlandim.easypod.fragment

import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.hlandim.easypod.domain.Episode
import com.hlandim.easypod.domain.PodCast

/**
 * Created by hlandim on 15/11/17.
 */
class PodCasListAdapter(var list: MutableMap<PodCast, List<Episode>>) : BaseExpandableListAdapter() {

    var podcastEpisodes: MutableMap<PodCast, List<Episode>> = mutableMapOf()


    override fun getGroup(p0: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasStableIds(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildrenCount(p0: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChild(p0: Int, p1: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupId(p0: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGroupCount(): Int = list.size
}