package com.hlandim.easypod.activity.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hlandim.easypod.R
import com.hlandim.easypod.domain.PodCast
import kotlinx.android.synthetic.main.search_list_item.view.*

/**
 * Created by hlandim on 15/11/17.
 */
class SearchListAdapter(private var list: MutableList<PodCast>
                        , private val context: Context,
                        val listener: SearchListListener) : RecyclerView.Adapter<SearchListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val podCast = list[position]
        holder?.title?.text = podCast.title
        if (podCast.signed) {
            holder?.btnAdd?.isEnabled = false
        } else {
            holder?.btnAdd?.isEnabled = true
            holder?.btnAdd?.setOnClickListener({
                listener.addPodCast(podCast)
                holder.btnAdd.isEnabled = false
            })
        }
    }

    override fun getItemCount(): Int = list.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.tv_title
        var btnAdd = itemView.btn_add
    }

    fun updateList(newList: MutableList<PodCast>) {
        list = newList
        notifyDataSetChanged()
    }

    interface SearchListListener {
        fun addPodCast(podCast: PodCast)
    }
}