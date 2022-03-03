package com.emamagic.signin.phone

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.emamagic.application.base.BaseFragment
import com.emamagic.application.utils.clickPartOfText
import com.emamagic.application.utils.onTextChange
import com.emamagic.application.utils.setColor
import com.emamagic.application.utils.toasty
import com.emamagic.application.utils.view.TypeFaceHelper
import com.emamagic.signin.R
import com.emamagic.signin.SigninViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.databinding.FragmentSigninWithPhoneBinding
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninWithPhoneFragment :
    BaseFragment<FragmentSigninWithPhoneBinding, SigninState, SigninAction, SigninViewModel>(),
    CountryCodePicker.OnCountrySelected {

    override val viewModel: SigninViewModel by hiltNavGraphViewModels(com.emamagic.navigation.R.id.singin_modules)
    private lateinit var countryPicker: CountryCodePicker


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {

        binding.inputEditText.onTextChange {
            viewModel.typingPhoneNumberEvent(it)
        }

    }

    override fun renderViewState(viewState: SigninState) {

    }

    override fun init() {
        setupCountryPicker()
    }

    override fun onClickListeners() {

        binding.txtSignupWithServerName.setOnClickListener { viewModel.signinWithServerNameClickedEvent() }
        binding.txtSignupWithUsername.setOnClickListener { viewModel.signinWithUserNameClickedEvent() }
        binding.btnSubmit.setOnClickListener { viewModel.submitPhoneNumberEvent() }

        binding.txtTermsPolicy.clickPartOfText("قوانین حریم خصوصی", setColor(com.emamagic.application.R.color.limoo_secondary), true) {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("")
//            startActivity(intent)
        }

        binding.txtTermsPolicy.clickPartOfText("شرایط و قوانین استفاده", setColor(com.emamagic.application.R.color.limoo_secondary), true) {
//            if (fragmentManager != null) {
//                InformationDialog().also {
//                    val b = Bundle()
//                    b.putString(InformationDialog.CONTENT, getString(R.string.privacy_policy_description))
//                    it.arguments = b
//                    it.show(requireFragmentManager(), "confirm dialog")
//                }
//            }
        }
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
        setCountryFlag(com.emamagic.application.R.drawable.ic_iran_flag)
        countryPicker = CountryCodePicker(requireContext())
        countryPicker.typeFace = TypeFaceHelper.getTypeface()
        countryPicker.hideNameCode(true)
        countryPicker.hidePhoneCode(true)
        countryPicker.setDefaultCountryUsingNameCodeAndApply(getString(com.emamagic.application.R.string.default_country_code))
        countryPicker.setOnCountrySelected(this)
    }

    private fun setCountryFlag(flag: Int) {
        val selectedFlag = ContextCompat.getDrawable(requireContext(), flag)
        val arrowDown = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_drop_down)
        val finalDrawable = LayerDrawable(arrayOf(selectedFlag, arrowDown))
        finalDrawable.setLayerInset(0, 0, 0, selectedFlag?.intrinsicWidth!!, 0)
        finalDrawable.setLayerInset(1, selectedFlag.intrinsicWidth -10  , -5, 0, -5)
        binding.inputEditText.setCompoundDrawablesWithIntrinsicBounds(
            finalDrawable, null, null, null)
    }

    override fun onCountrySelect(flag: Int) {
        setCountryFlag(flag)
    }

}