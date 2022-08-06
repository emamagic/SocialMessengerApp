package com.emamagic.conversations

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.common_ui.conversation
import com.emamagic.common_ui.conversationType
import com.emamagic.conversations.databinding.FragmentConversationsBinding
import com.emamagic.conversations.contract.ConversationsEvent
import com.emamagic.conversations.contract.ConversationsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationsFragment: BaseFragment<FragmentConversationsBinding, ConversationsState, ConversationsEvent, ConversationsRouter.Routes, ConversationsViewModel>() {

    override val viewModel: ConversationsViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.conversations_graph)

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: ConversationsState) {
        binding.recyclerConversation.withModels {
            viewState.conversations.forEach { item ->
                when (item) {
                    is ConversationWrapper.Header -> conversationType {
                            id(item.idOrType)
                            displayName(getString(item.displayNameId))
                            onClickListener { _ ->
//                                item.isExpanded = !item.isExpanded
//                                viewModel.toggleExpandability(item)
                            }
                        }

                    is ConversationWrapper.Conversation -> conversation {
                            id(item.idOrType)
                            conversationName(item.entity.directUserDisplayName ?: item.entity.displayName)
                            onClickListener { _ ->
                                viewModel.goToConversation(item.entity.workspaceId, item.entity.id)
                            }
                        }

                }
            }
        }
    }


}