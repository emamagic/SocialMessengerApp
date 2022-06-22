package com.emamagic.login.policy

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.emamagic.base.base.BaseDialogFragment
import com.emamagic.login.databinding.DialogTermsPolicyBinding

class TermsPolicyDialog: BaseDialogFragment<DialogTermsPolicyBinding>() {

    private val args: TermsPolicyDialogArgs by navArgs()

    override fun onDialogCreated(view: View, savedInstanceState: Bundle?) {

        binding.content.text = getString(args.description)

        binding.btnOk.setOnClickListener { dismiss() }
    }
}