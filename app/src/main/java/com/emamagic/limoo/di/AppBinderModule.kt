package com.emamagic.limoo.di

import com.emamagic.base.appinitializer.AppInitializer
import com.emamagic.limoo.appinitializers.TypeFaceInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBinderModule {

    @Binds
    @IntoSet
    abstract fun bindTypeFaceInitializer(typeFaceInitializer: TypeFaceInitializer): AppInitializer


}