package com.pixis.traktTV.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pixis.traktTV.base.adapters.BaseViewHolder
import com.pixis.traktTV.base.adapters.BindableViewHolder

interface OnItemClickListener<T> {
    fun onTrackedItemClicked(item: T, viewHolder: BindableViewHolder<T>, position: Int)
    fun onTrackedItemLongClick(item: T, viewHolder: BindableViewHolder<T>, position: Int)
}

class SingleItemAdapter<T>(private val viewHolder: BaseViewHolder<T>) : RecyclerView.Adapter<BindableViewHolder<T>>() {

    private var mData: List<T> = emptyList()

    private var listenerTracked: OnItemClickListener<T>? = null

    fun setListener(listener: OnItemClickListener<T>) {
        this.listenerTracked = listener
    }

    fun update(data: List<T>) {
        this.mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<T> {
        val v = LayoutInflater.from(parent.context).inflate(viewHolder.layoutId, parent, false)
        return BindableViewHolder<T>(v, viewHolder)
    }

    override fun onBindViewHolder(holder: BindableViewHolder<T>, position: Int) {
        val mTrackedItem = mData[position]
        holder.onBind(mTrackedItem)

        holder.itemView.setOnLongClickListener {
            listenerTracked?.onTrackedItemLongClick(mTrackedItem, holder, position)
            return@setOnLongClickListener true
        }

        holder.itemView.setOnClickListener {
            listenerTracked?.onTrackedItemClicked(mTrackedItem, holder, position)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}
