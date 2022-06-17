package com.emamagic.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.emamagic.base.*
import com.emamagic.base.R
import com.emamagic.mvi.EVENT
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.State

abstract class BaseFragment<DB : ViewDataBinding, STATE : State, ACTION : EVENT, VM : BaseViewModel<STATE, ACTION>> :
    Fragment() {

    private var _binding: DB? = null
    protected val binding: DB get() = _binding!!
    private lateinit var loading: FrameLayout
    private var callback: OnBackPressedCallback? = null
    protected val TAG = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getResId(this.javaClass.simpleName), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingId = resources.getIdentifier("loading", "id",requireActivity().packageName)
        loading = requireActivity().getRootView().findViewById(loadingId)!!
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { renderViewState(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffect.collect { renderDefaultViewEffect(it) }
        }
        onFragmentCreated(view, savedInstanceState)
//        setInitialFunctionsForRefreshing(viewModel.getInitialFunctions())
    }

    abstract val viewModel: VM

    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()

    /**
     * convention naming for fragment class and fragment layout does matter
     * TestOneFragment    (name of fragment)
     * fragment_test_one  (name of layout)
     *
     * It can be potentially removed when obfuscating / minimizing your code in a release build.
     * In such case you need to add a proguard rule to keep some specific resources to avoid their removal.
     * */
    @LayoutRes
    fun getResId(className: String): Int {
        try {
            var result = ""
            val words = className.split(regex = Regex(
                "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])")
            ).toMutableList()
            words.removeLast()
            words.add(0, "fragment")
            words.forEachIndexed { index, s ->
                result += if (words.size - 1 != index) "${s.lowercase()}_"
                else s.lowercase()
            }
            return  resources.getIdentifier(result, "layout", requireActivity().packageName)
        } catch (e: Exception) {
            throw Exception("BaseFragment -> could not find correct layout for ${this.javaClass.simpleName}. it might be for wrong convention naming")
        }
    }

    abstract fun onFragmentCreated(view: View, savedInstanceState: Bundle?)

    abstract fun renderViewState(viewState: STATE)

    open fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean = false

    open fun enableUiComponent(): Boolean = false

    open fun disableUiComponent(): Boolean = false

    open fun showDialog(message: String, proceedTitle: String?, cancelTitle: String?): Boolean = false

    open fun showSnackBar(message: String, timeOut: Long? = 3000): Boolean = false

    private fun renderDefaultViewEffect(viewEffect: BaseEffect) {
        when (viewEffect) {
            is BaseEffect.Toast -> Toast.makeText(requireContext(), viewEffect.message, Toast.LENGTH_SHORT).show()
            is BaseEffect.ShowLoading -> showLoading(viewEffect.isDim)
            is BaseEffect.HideLoading -> hideLoading()
            is BaseEffect.HideKeyboard -> hideKeyboard()
            is BaseEffect.Dialog ->
                if (!showDialog(viewEffect.message, viewEffect.proceedTitle, viewEffect.cancelTitle))
                    throw Exception("BaseFragment -> ShowDialog Does Not Implemented")
            is BaseEffect.SnackBar ->
                if (!showSnackBar(viewEffect.message, viewEffect.timeOut))
                    throw Exception("BaseFragment -> ShowSnackBar Does Not Implemented")
            is BaseEffect.EnableUiComponent ->
                if (!enableUiComponent())
                    throw Exception("BaseFragment -> EnableUiComponent Does Not Implemented")
            is BaseEffect.DisableUiComponent ->
                if (!disableUiComponent())
                    throw Exception("BaseFragment -> DisableUiComponent Does Not Implemented")
            is BaseEffect.NavigateTo -> findNavController().navigate(
                viewEffect.directions,
                getExtras()
            )
            is BaseEffect.NavigateBack -> findNavController().navigateUp()
            is BaseEffect.NeedToSignIn ->
                findNavController().navigate(
                    NavDeepLinkRequest.Builder.fromUri("android-app://limoo.im.app/signin".toUri()).build(),
                    NavOptions.Builder().setPopUpTo(findNavController().currentDestination?.id!!, true).build())

            else ->
                if (!renderCustomViewEffect(viewEffect))
                    throw Exception("BaseFragment -> RenderViewEffect Does Not Implemented")
        }
    }

    fun onFragmentBackPressed(owner: LifecycleOwner, call: () -> Unit) {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                call()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(owner, callback!!)
    }

    open fun showLoading(isDim: Boolean = false) {
        if (isDim) loading.setBackgroundColor(setColor(R.color.dim_color))
        loading.visible()
    }

    open fun hideLoading() {
        loading.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (callback != null) {
            callback?.isEnabled = false
            callback?.remove()
        }
    }
}