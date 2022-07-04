package com.emamagic.conversations

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.emamagic.domain.entities.ConversationEntity

class ConversationsController: PagingDataEpoxyController<ConversationEntity>() {


    override fun buildItemModel(currentPosition: Int, item: ConversationEntity?): EpoxyModel<*> {
        return RowConversation(
            conversationEntity = item!!,
            onClick = { conversationId ->  }
        )
    }

    data class RowConversation(
        val conversationEntity: ConversationEntity,
        val onClick: (Int) -> Unit
    ): DataBindingEpoxyModel() {

        override fun getDefaultLayout(): Int = com.emamagic.base.R.layout.row_conversation

        override fun setDataBindingVariables(binding: ViewDataBinding?) {

        }
    }
}