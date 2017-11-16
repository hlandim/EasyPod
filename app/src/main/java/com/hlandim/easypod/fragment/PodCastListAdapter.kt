package com.hlandim.easypod.fragment

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hlandim.easypod.R
import com.hlandim.easypod.domain.PodCast
import kotlinx.android.synthetic.main.podcast_list_item.view.*

/**
 * Created by hlandim on 15/11/17.
 */
class PodCastListAdapter(var list: List<PodCast>, private val context: Context) : RecyclerView.Adapter<PodCastListAdapter.ListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.podcast_list_item, parent, false)
        return PodCastListAdapter.ListHolder(view)
    }

    override fun onBindViewHolder(holder: ListHolder?, position: Int) {
        val podCast = list[position]
        Glide.with(context).load(podCast.imgThumbUrl).into(holder?.img)
    }

    override fun getItemCount(): Int = list.size


    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.img_thumb
    }

    fun update(newList: List<PodCast>){
        list = newList
        notifyDataSetChanged()
    }

}