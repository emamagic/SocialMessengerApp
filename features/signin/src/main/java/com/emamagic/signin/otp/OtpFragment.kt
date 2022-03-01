package com.emamagic.signin.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.emamagic.application.base.BaseFragment
import com.emamagic.signin.SigninViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class OtpFragment :
    BaseFragment<FragmentOtpBinding, SigninState, SigninAction, SigninViewModel>()  {

    override val viewModel: SigninViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.singin_modules)
    private lateinit var expirationTimer: ExpirationTimer

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun renderViewState(viewState: SigninState) {

    }

    override fun init() {
        expirationTimer = ExpirationTimer(binding.txtTimer) { viewModel.otpExpired() }
        expirationTimer.start()
    }

    override fun onClickListeners() {

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
        expirationTimer.cancel()
    }

}