package com.emamagic.data.di

import dagger.assisted.AssistedFactory
import okhttp3.OkHttpClient

@AssistedFactory
interface RetrofitFactory {

    fun create(baseUrl: String, okHttpClient: OkHttpClient): RetrofitProviderImpl

}