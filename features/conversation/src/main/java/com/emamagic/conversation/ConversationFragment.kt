package com.emamagic.conversation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import com.emamagic.conversation.databinding.FragmentConversationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversationFragment: BaseFragment<FragmentConversationBinding, ConversationState, ConversationAction, ConversationRouter.Routes, ConversationViewModel>() {

    override val viewModel: ConversationViewModel by viewModels()

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: ConversationState) {

//        binding.recyclerConversation.withModels {
//
//        }


    }


}