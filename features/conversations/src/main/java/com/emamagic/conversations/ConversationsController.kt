package com.emamagic.conversations

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.emamagic.domain.entities.Conversation

class ConversationsController: PagingDataEpoxyController<Conversation>() {


    override fun buildItemModel(currentPosition: Int, item: Conversation?): EpoxyModel<*> {
        return RowConversation(
            conversation = item!!,
            onClick = { conversationId ->  }
        )
    }

    data class RowConversation(
        val conversation: Conversation,
        val onClick: (Int) -> Unit
    ): DataBindingEpoxyModel() {

        override fun getDefaultLayout(): Int = com.emamagic.base.R.layout.row_conversation

        override fun setDataBindingVariables(binding: ViewDataBinding?) {

        }
    }
}