package com.emamagic.data.di

import retrofit2.Retrofit

interface RetrofitProvider {
    fun provideRetrofit(): Retrofit
}