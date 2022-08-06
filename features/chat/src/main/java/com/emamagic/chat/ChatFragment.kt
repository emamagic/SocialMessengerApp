package com.emamagic.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import com.emamagic.chat.databinding.FragmentChatBinding
import com.emamagic.common_ui.base.BaseFragment

class ChatFragment: BaseFragment<FragmentChatBinding, ChatContract.ChatState, ChatContract.ChatEvent, ChatContract.ChatRouter.Routes, ChatViewModel>() {

    override val viewModel: ChatViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.chat_graph)

    private val args: ChatFragmentArgs by navArgs()


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.workspaceId.text = args.workspaceName
        binding.conversationId.text = args.conversationId
        binding.messageId.text = args.messageId

    }

    override fun renderViewState(viewState: ChatContract.ChatState) {


    }
}