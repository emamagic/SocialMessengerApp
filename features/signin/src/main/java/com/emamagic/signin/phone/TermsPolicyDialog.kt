package com.emamagic.signin.phone

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.emamagic.application.base.BaseDialogFragment
import com.emamagic.signin.R
import com.emamagic.signin.databinding.DialogTermsPolicyBinding

class TermsPolicyDialog: BaseDialogFragment<DialogTermsPolicyBinding>() {

    private val args: TermsPolicyDialogArgs by navArgs()


    override fun onDialogCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun init() {
        binding.content.text = args.description
    }

    override fun onClickListeners() {
        binding.btnOk.setOnClickListener { dismiss() }
    }

}