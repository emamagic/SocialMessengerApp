package com.emamagic.signin.phone

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.base.clickPartOfText
import com.emamagic.base.getDrawableCompat
import com.emamagic.base.onTextChange
import com.emamagic.base.setColor
import com.emamagic.base.base.BaseFragment
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.SigninEffect
import com.emamagic.signin.R
import com.emamagic.signin.LoginViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.databinding.FragmentSigninWithPhoneBinding
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninWithPhoneFragment :
    BaseFragment<FragmentSigninWithPhoneBinding, SigninState, SigninAction, LoginViewModel>(),
    CountryCodePicker.OnCountrySelected {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.singin_modules)
    private lateinit var countryPicker: CountryCodePicker


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        setupCountryPicker()
        binding.inputEditText.onTextChange {
            viewModel.typingPhoneNumberEvent(it)
        }
        binding.txtSignupWithServerName.setOnClickListener { viewModel.signinWithServerNameClickedEvent() }
        binding.txtSignupWithUsername.setOnClickListener { viewModel.signinWithUserNameClickedEvent() }
        binding.btnSubmit.setOnClickListener {
            viewModel.submitPhoneNumberEvent(binding.inputEditText.text.toString(), countryPicker.selectedCountryCodeWithPlus)
        }

        binding.txtTermsPolicy.clickPartOfText("قوانین حریم خصوصی",
            setColor(com.emamagic.base.R.color.limoo_secondary), true) {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("")
//            startActivity(intent)
        }

        binding.txtTermsPolicy.clickPartOfText("شرایط و قوانین استفاده",
            setColor(com.emamagic.base.R.color.limoo_secondary), true) {
            viewModel.submitTermsPolicyEvent()
        }
        binding.inputEditText.setDrawableClickListener { countryPicker.mRlyClickConsumer.performClick() }

    }

    override fun renderViewState(viewState: SigninState) {
//        if (viewState.serverConfigLoaded) toasty("serverConfig loadded")
    }

    override fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean {
        when (viewEffect) {
            SigninEffect.InvalidPhoneNumber -> binding.validatorInput.invalidateInput()
            else -> {}
        }
        return true
    }

    override fun enableUiComponent(): Boolean {
        binding.btnSubmit.enable()
        return true
    }

    override fun disableUiComponent(): Boolean {
        binding.btnSubmit.disable()
        return true
    }

    private fun setupCountryPicker() {
        setCountryFlag(com.emamagic.base.R.drawable.ic_iran_flag)
        countryPicker = CountryCodePicker(
            requireContext()
        )
//        countryPicker.typeFace = TypeFaceImpl.getTypeface()
        countryPicker.hideNameCode(true)
        countryPicker.hidePhoneCode(true)
        countryPicker.setDefaultCountryUsingNameCodeAndApply(getString(com.emamagic.base.R.string.default_country_code))
        countryPicker.setOnCountrySelected(this)
    }

    private fun setCountryFlag(flag: Int) {
        val selectedFlag = getDrawableCompat(flag)
        val arrowDown = getDrawableCompat(R.drawable.ic_arrow_drop_down)
        val finalDrawable = LayerDrawable(arrayOf(selectedFlag, arrowDown))
        finalDrawable.setLayerInset(0, 0, 0, selectedFlag?.intrinsicWidth!!, 0)
        finalDrawable.setLayerInset(1, selectedFlag.intrinsicWidth -10  , -5, 0, -5)
        binding.inputEditText.setCompoundDrawablesWithIntrinsicBounds(
            finalDrawable, null, null, null)
    }

    override fun onCountrySelect(flag: Int) {
        setCountryFlag(flag)
    }

    override fun showLoading(isDim: Boolean) {
        binding.btnSubmit.showProgress()
    }

    override fun hideLoading() {
        binding.btnSubmit.hideProgress()
    }

}