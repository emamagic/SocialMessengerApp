package com.emamagic.network.di

import retrofit2.Retrofit

interface RetrofitProvider {
    fun provideRetrofit(): Retrofit
}