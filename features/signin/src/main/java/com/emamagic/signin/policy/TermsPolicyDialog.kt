package com.emamagic.signin.policy

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.emamagic.base.base.BaseDialogFragment
import com.emamagic.signin.databinding.DialogTermsPolicyBinding

class TermsPolicyDialog: BaseDialogFragment<DialogTermsPolicyBinding>() {

    private val args: TermsPolicyDialogArgs by navArgs()


    override fun onDialogCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun init() {
        binding.content.text = getString(args.description)
    }

    override fun onClickListeners() {
        binding.btnOk.setOnClickListener { dismiss() }
    }

}