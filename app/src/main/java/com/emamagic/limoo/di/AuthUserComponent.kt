package com.emamagic.limoo.di

import com.emamagic.core.AuthUserScope
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@AuthUserScope
@DefineComponent(parent = SingletonComponent::class)
interface AuthUserComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): AuthUserComponent
    }
}