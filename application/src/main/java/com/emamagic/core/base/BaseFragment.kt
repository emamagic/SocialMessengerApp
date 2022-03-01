package com.emamagic.core.base

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.emamagic.core.R
import com.emamagic.core.extension.gone
import com.emamagic.core.extension.visible
import com.emamagic.core.utils.ToastyMode
import com.emamagic.core.utils.keyboard.getRootView

abstract class BaseFragment<DB : ViewDataBinding, STATE : State, ACTION : Action, VM : BaseViewModel<STATE, ACTION>> :
    Fragment() {


    private var _binding: DB? = null
    protected val binding: DB get() = _binding!!
    private lateinit var loading: FrameLayout
    private var callback: OnBackPressedCallback? = null

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
            viewModel.viewState.collect { renderViewState(it) }
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
     * convention naming for fragment class and fragment layout
     * */
    @LayoutRes
    fun getResId(className: String): Int {
        var result = ""
        val words = className.split(regex = Regex(
            "(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])")
        )
        val orderedWords = words.reversed()
        orderedWords.forEachIndexed { index, s ->
            result += if (orderedWords.size-1 != index) "${s.lowercase()}_"
            else s.lowercase()
        }
        return  resources.getIdentifier(result, "layout", requireActivity().packageName)
    }

    abstract fun onFragmentCreated(view: View, savedInstanceState: Bundle?)

    abstract fun renderViewState(viewState: STATE)

    open fun renderCustomViewEffect(viewEffect: BaseEffect): Boolean = false

    private fun renderDefaultViewEffect(viewEffect: BaseEffect) {
        when (viewEffect) {
            is BaseEffect.ShowToast -> if (viewEffect.mode == ToastyMode.MODE_TOAST_DEFAULT) {
                Toast.makeText(requireContext(), viewEffect.message, Toast.LENGTH_SHORT).show()
            } else {
                toasty(viewEffect.message, viewEffect.mode)
            }
            is BaseEffect.ShowLoading -> showLoading(viewEffect.isDim)
            is BaseEffect.HideLoading -> hideLoading()
            is BaseEffect.NavigateTo -> findNavController().navigate(
                viewEffect.directions,
                getExtras()
            )
            is BaseEffect.NavigateBack -> findNavController().navigateUp()
            is BaseEffect.ShowAlert -> {}
            else ->
                if (!renderCustomViewEffect(viewEffect))
                    throw Exception("RenderViewEffect Does Not Implemented")
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

    private fun showLoading(isDim: Boolean = false) {
        if (isDim) loading.setBackgroundColor(Color.parseColor("#cc000000"))
        loading.visible()
    }

    private fun hideLoading() {
        loading.gone()
    }

    private fun toasty(title: String, @ToastyMode selectedMode: Int? = null) {
        val layout = layoutInflater.inflate(
            R.layout.layout_toast,
            requireView().findViewById(R.id.toast_root)
        )
        when (selectedMode) {

            ToastyMode.MODE_TOAST_SUCCESS -> {
                layout.findViewById<ImageView>(R.id.toast_img)
                    .setImageResource(R.drawable.ic_corroct_toast)
                layout.findViewById<ConstraintLayout>(R.id.toast_root)
                    .setBackgroundResource(R.drawable.bg_corroct_toast)
            }
            ToastyMode.MODE_TOAST_WARNING -> {
                layout.findViewById<ImageView>(R.id.toast_img)
                    .setImageResource(R.drawable.ic_warning_toast)
                layout.findViewById<ConstraintLayout>(R.id.toast_root)
                    .setBackgroundResource(R.drawable.bg_warning_toast)
                layout.findViewById<TextView>(R.id.toast_txt)
                    .setTextColor(resources.getColor(android.R.color.black))
            }
            ToastyMode.MODE_TOAST_ERROR -> {
                layout.findViewById<ImageView>(R.id.toast_img)
                    .setImageResource(R.drawable.ic_error_toast)
                layout.findViewById<ConstraintLayout>(R.id.toast_root)
                    .setBackgroundResource(R.drawable.bg_error_toast)
            }
            else -> {
                Toast.makeText(requireContext(), title, Toast.LENGTH_LONG).show()
            }

        }

        layout.findViewById<TextView>(R.id.toast_txt).text = title
        if (selectedMode != null) {
            Toast(requireActivity()).apply {
                setGravity(Gravity.BOTTOM, 0, 100)
                duration = Toast.LENGTH_LONG
                view = layout
            }.show()
        }
    }


    interface DialogListener {
        fun onAccept(dialog: Dialog)
        fun onDecline(dialog: Dialog)
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