package com.emamagic.signin.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.base.BaseFragment
import com.emamagic.signin.LoginViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class OtpFragment :
    BaseFragment<FragmentOtpBinding, SigninState, SigninAction, LoginViewModel>()  {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.singin_modules)
    private var expirationTimer: ExpirationTimer? = null

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        expirationTimer = ExpirationTimer(binding.txtTimer) { viewModel.otpExpired() }
        expirationTimer?.start()
        binding.btnSubmit.setOnClickListener { viewModel.submitOtpEvent(binding.squareFieldPin.text.toString()) }
    }

    override fun renderViewState(viewState: SigninState) {

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