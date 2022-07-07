package com.emamagic.limoo.di

import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.limoo.appinitializers.CacheInitializer
import com.emamagic.limoo.appinitializers.ServerConfigInitializer
import com.emamagic.limoo.appinitializers.TypeFaceInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInitializerBinderModule {

    @Binds
    @IntoSet
    abstract fun bindTypeFaceInitializer(typeFaceInitializer: TypeFaceInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindCacheInitializer(cacheInitializer: CacheInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindServerConfigInitializer(serverConfigInitializer: ServerConfigInitializer): AppInitializer

//    @Binds
//    @IntoSet
//    abstract fun bindTimberInitializer(timberInitializer: TimberInitializer): AppInitializer


}