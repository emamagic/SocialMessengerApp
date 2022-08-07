package com.emamagic.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.common_ui.widget.FontTextView
import com.emamagic.domain.entities.MessageEntity

class MessagePagerAdapter: PagingDataAdapter<MessageEntity,MessagePagerAdapter.MessagePagerViewHolder>(Comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagePagerViewHolder =
        MessagePagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_chat, parent, false))

    override fun onBindViewHolder(holder: MessagePagerViewHolder, position: Int) {
        if (position > -1) {
            holder.bind(getItem(position))
        }
    }

    // todo implement by data binding
    class MessagePagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txt = itemView.findViewById<FontTextView>(R.id.txt)
        fun bind(item: MessageEntity?) = with(itemView) {
            txt.text = item?.text
        }
    }

    companion object {
        object Comparator: DiffUtil.ItemCallback<MessageEntity>() {
            override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean = oldItem == newItem
        }
    }

}