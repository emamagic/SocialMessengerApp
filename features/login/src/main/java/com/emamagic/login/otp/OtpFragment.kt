package com.emamagic.login.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginAction
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class OtpFragment :
    BaseFragment<FragmentOtpBinding, LoginState, LoginAction, LoginViewModel>()  {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_modules)
    private var expirationTimer: ExpirationTimer? = null

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        expirationTimer = ExpirationTimer(binding.txtTimer) { viewModel.otpExpired() }
        expirationTimer?.start()
        binding.btnSubmit.setOnClickListener { viewModel.submitOtpEvent(binding.squareFieldPin.text.toString()) }
    }

    override fun renderViewState(viewState: LoginState) {

    }

    override fun invalidInputValue(message: String?, type: Any?): Boolean {
        Toast.makeText(requireContext(), "کد وارد شده صحیح نمی باشد.", Toast.LENGTH_SHORT).show()
        return true
    }

    class ExpirationTimer(timer: TextView, val finished: () -> Unit) : CountDownTimer(60000, 1000) {
        private val timerTextView = WeakReference(timer)
        override fun onTick(millisUntilFinished: Long) {
            var sec = "0"
            val second = millisUntilFinished % 60000 / 1000
            sec = if (millisUntilFinished % 60000 / 1000 < 10) {
                sec + second
            } else {
                "" + second
            }
            timerTextView.get()?.text =
                (millisUntilFinished / 60000).toString() + ":" + sec
        }

        override fun onFinish() { finished() }
    }

    override fun onDestroy() {
        super.onDestroy()
        expirationTimer?.cancel()
        expirationTimer = null
    }

}