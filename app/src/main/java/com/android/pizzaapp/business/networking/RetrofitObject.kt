package com.android.pizzaapp.business.networking

import com.android.pizzaapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitObject {
    private val okhttpBuilder: OkHttpClient
        get() {
            val okhttpBuilder = OkHttpClient.Builder()
            okhttpBuilder.callTimeout(30, TimeUnit.SECONDS)
            okhttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
            okhttpBuilder.readTimeout(30, TimeUnit.SECONDS)
            okhttpBuilder.writeTimeout(30, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                okhttpBuilder.addInterceptor(logging)
            }
            return okhttpBuilder.build()
        }

    val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okhttpBuilder)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .build()
}