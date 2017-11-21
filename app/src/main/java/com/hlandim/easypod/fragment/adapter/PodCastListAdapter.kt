package com.hlandim.easypod.fragment.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.bumptech.glide.Glide
import com.hlandim.easypod.R
import com.hlandim.easypod.domain.PodCast
import kotlinx.android.synthetic.main.podcast_list_item_grid.view.*

/**
 * Created by hlandim on 15/11/17.
 */
class PodCastListAdapter(var list: List<PodCastSync>, private val context: Context, private val listener: PodCastListListener) : RecyclerView.Adapter<PodCastListAdapter.ListHolder>() {

    private var clickedPosition = 0;

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.podcast_list_item_grid, parent, false)
        return ListHolder(view)
    }

    override fun onBindViewHolder(holder: ListHolder?, position: Int) {
        val podCastSync = list[position]
        val podCast = podCastSync.podCast
        Glide.with(context).load(podCast.imgThumbUrl).into(holder?.img)
        holder?.itemView?.setOnClickListener {
            listener.onPodCastClicked(podCast, position)

            clickedPosition = position

            notifyDataSetChanged()
        }
        holder?.pbSync?.visibility = if (podCastSync.isSyncing) View.VISIBLE else View.GONE

        holder?.itemView?.setOnLongClickListener { view ->
            val popUpMenu = PopupMenu(view.context, view)
            popUpMenu.inflate(R.menu.podcast_list_popup_menu)
            popUpMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.popup_menu_remove -> listener.onPodCastRemoveClicked(podCast)
                }
                true
            }
            popUpMenu.show()
            true
        }

        if (clickedPosition == position) {
            holder?.itemView?.setBackgroundColor(ContextCompat.getColor(context, R.color.search_background_overlay))
//            val parms = holder?.itemView?.layoutParams
//            parms?.width = parms?.width!! + 30
//            parms.height = parms.height + 30
//            holder.itemView.layoutParams = parms
//            ViewCompat.setElevation(holder.itemView, 30F)
        }
    }

    override fun getItemCount(): Int = list.size


    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.img_thumb
        val pbSync = itemView.pb_sync
    }

    fun update(newList: List<PodCastSync>) {
        list = newList
        notifyDataSetChanged()
    }

    fun finishSync(podCast: PodCast) {
        val pcSync = PodCastSync(podCast, false)
        val index = list.indexOf(pcSync)
        if (index != -1) {
            list[index].isSyncing = false
            notifyDataSetChanged()
        }
    }

    interface PodCastListListener {
        fun onPodCastClicked(podCast: PodCast, position: Int)
        fun onPodCastRemoveClicked(podCast: PodCast)
    }

    data class PodCastSync(val podCast: PodCast, var isSyncing: Boolean = true) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other == null || javaClass != other.javaClass) return false
            val that = other as PodCastSync?
            return podCast.id == that?.podCast?.id
        }

        override fun hashCode(): Int = podCast.id.hashCode()
    }

}