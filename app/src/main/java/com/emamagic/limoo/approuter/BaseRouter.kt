package com.emamagic.limoo.approuter

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.emamagic.navigation.lifecycle.LiveDataEvent
import com.emamagic.navigation.lifecycle.consume
import com.emamagic.navigation.router.Router
import java.io.Serializable

const val TAG = "BaseRouter"
abstract class BaseRouter: Router {

//    @get:IdRes
//    abstract val graphId: Int

    override fun pop(instance: Fragment) {
        instance.findNavController().popBackStack()
    }


    // Controller Extensions
    fun <T : Serializable> NavController.observeResultForKey(
        owner: LifecycleOwner,
        key: String,
        callback: (value: T) -> Unit
    ) {
        currentBackStackEntry?.savedStateHandle
            ?.let { savedStateHandle ->
                savedStateHandle.getLiveData<LiveDataEvent<T>>(key).let { event ->
                    event.removeObservers(owner)
                    event.consume(owner) {
                        savedStateHandle.remove<T>(key)
                        callback(it)
                    }
                }
            }
    }

    fun NavController.observeResultForKey(
        owner: LifecycleOwner,
        key: String,
        callback: () -> Unit
    ) {
        currentBackStackEntry?.savedStateHandle
            ?.let { savedStateHandle ->
                savedStateHandle.getLiveData<LiveDataEvent<Unit>>(key).let { event ->
                    event.removeObservers(owner)
                    event.consume(owner) {
                        savedStateHandle.remove<Unit>(key)
                        callback()
                    }
                }
            }
    }

    fun <T : Serializable> NavController.postResult(key: String, value: T) {
        previousBackStackEntry?.savedStateHandle?.set(key, LiveDataEvent(value))
    }

    fun NavController.postResult(key: String) {
        previousBackStackEntry?.savedStateHandle?.set(key, LiveDataEvent(Unit))
    }

}