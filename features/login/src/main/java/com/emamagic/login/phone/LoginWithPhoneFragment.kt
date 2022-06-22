package com.emamagic.login.phone

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
import com.emamagic.login.LoginViewModel
import com.emamagic.login.R
import com.emamagic.login.contract.LoginAction
import com.emamagic.login.contract.LoginState
import com.emamagic.login.databinding.FragmentLoginWithPhoneBinding
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginWithPhoneFragment :
    BaseFragment<FragmentLoginWithPhoneBinding, LoginState, LoginAction, LoginViewModel>(),
    CountryCodePicker.OnCountrySelected {

    override val viewModel: LoginViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.login_modules)
    private lateinit var countryPicker: CountryCodePicker


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        setupCountryPicker()
        binding.inputEditText.onTextChange {
            viewModel.typingPhoneNumberEvent(it)
        }
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

    override fun renderViewState(viewState: LoginState) {
//        if (viewState.serverConfigLoaded) toasty("serverConfig loadded")
    }

    override fun invalidInputValue(message: String?, type: Any?): Boolean {
        binding.validatorInput.invalidateInput()
        return true
    }

    override fun enableUiComponent(type: Any?): Boolean {
        binding.btnSubmit.enable()
        return true
    }

    override fun disableUiComponent(type: Any?): Boolean {
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

    override fun showLoading(isDim: Boolean, type: Any?) {
        binding.btnSubmit.showProgress()
    }

    override fun hideLoading(type: Any?) {
        binding.btnSubmit.hideProgress()
    }

}