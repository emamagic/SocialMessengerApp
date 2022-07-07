package com.emamagic.login.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.common_ui.base.BaseFragment
import com.emamagic.login.contract.LoginRouter
import com.emamagic.login.LoginViewModel
import com.emamagic.login.contract.LoginEvent
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference

@AndroidEntryPoint
class OtpFragment :
    BaseFragment<FragmentOtpBinding, LoginState, LoginEvent, LoginRouter.Routes, LoginViewModel>()  {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_graph)
    private var expirationTimer: ExpirationTimer? = null

    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        expirationTimer = ExpirationTimer(binding.txtTimer) { viewModel.setEvent(LoginEvent.OtpExpiredEvent) }
        expirationTimer?.start()
        binding.btnSubmit.setOnClickListener {
            val deviceId = Settings.Secure.getString(
                requireContext().contentResolver,
                Settings.Secure.ANDROID_ID
            )
            viewModel.setEvent(LoginEvent.SubmitOtpEvent(binding.squareFieldPin.text.toString(), deviceId))
        }
    }

    override fun renderViewState(viewState: LoginState) {

    }

    override fun invalidInput(message: String?, type: Any?): Boolean {
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