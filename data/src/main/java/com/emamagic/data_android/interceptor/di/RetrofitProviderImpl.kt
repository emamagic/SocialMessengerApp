package com.emamagic.data_android.interceptor.di

import com.emamagic.data_android.interceptor.ResponseConverter
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class RetrofitProviderImpl @AssistedInject constructor(
   @Assisted
   private val baseUrl: String,
   @Assisted
   private val client: OkHttpClient,
): RetrofitProvider {

   override fun provideRetrofit(): Retrofit =
      Retrofit.Builder()
         .addConverterFactory(
            ResponseConverter(
               GsonBuilder()
                  .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                  .create()
            )
         )
         .client(client)
         .baseUrl(baseUrl)
         .build()


}