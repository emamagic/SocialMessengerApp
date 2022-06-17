package com.emamagic.data_android.interceptor.di

import retrofit2.Retrofit

interface RetrofitProvider {
    fun provideRetrofit(): Retrofit
}