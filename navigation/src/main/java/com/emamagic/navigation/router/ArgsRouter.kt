package com.emamagic.navigation.router

import androidx.fragment.app.Fragment
import java.io.Serializable

interface ArgsRouter<Args: Serializable> {
    fun args(instance: Fragment): Args
}