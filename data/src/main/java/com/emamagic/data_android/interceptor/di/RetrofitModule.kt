package com.emamagic.data_android.interceptor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RetrofitModule {

    @Binds
    abstract fun provideRetrofitFactory(retrofitProviderImpl: RetrofitProviderImpl): RetrofitFactory


}
