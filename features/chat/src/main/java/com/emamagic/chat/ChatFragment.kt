package com.emamagic.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.*
import com.airbnb.epoxy.paging3.PagedListEpoxyController
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.emamagic.chat.databinding.FragmentChatBinding
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.domain.entities.MessageEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatFragment: BaseFragment<FragmentChatBinding, ChatContract.ChatState, ChatContract.ChatEvent, ChatContract.ChatRouter.Routes, ChatViewModel>() {

    override val viewModel: ChatViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.chat_graph)

    private val args: ChatFragmentArgs by navArgs()

    private val adapter = MessagePagerAdapter()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerView.adapter = adapter

        viewModel.setEvent(ChatContract.ChatEvent.LoadMessage(args.workspaceName, args.conversationId))

    }

    override fun renderViewState(viewState: ChatContract.ChatState) {

        if (viewState.messages != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(viewState.messages)
            }
        }


    }

}