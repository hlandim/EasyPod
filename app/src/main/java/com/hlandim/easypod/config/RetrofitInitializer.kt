package com.hlandim.easypod.config

import com.hlandim.easypod.logic.web.PodCastService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hlandim on 09/11/17.
 */
class RetrofitInitializer {

    private var retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        retrofit = Retrofit.Builder()
                .baseUrl("https://www.audiosear.ch/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()

    }


    fun podCastService() = retrofit.create(PodCastService::class.java)
}