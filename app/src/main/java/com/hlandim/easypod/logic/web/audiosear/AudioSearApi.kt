package com.hlandim.easypod.logic.web.audiosear

import com.hlandim.easypod.domain.PodCast
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hlandim on 10/11/17.
 */
class AudioSearApi {

    var token: String = ""

    private val service: AudioSearService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.audiosear.ch/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
        service = retrofit.create(AudioSearService::class.java)

    }


    private object Holder {
        val INSTANCE = AudioSearApi()
    }

    companion object {
        val instance: AudioSearApi by lazy { Holder.INSTANCE }
        val clientId: String by lazy { "6277cf8e5c466698703fd3fd14e7a8541e272125994cc1e91ebf6b8353fd0ca0" }
        val clientSecrete: String by lazy { "2451a8f927bd3ab24b0b62553cb1db3a07dc2e726286be1c2c0090fcd8968d16" }
        val redirectUri: String by lazy { "urn:ietf:wg:oauth:2.0:oob" }
    }

    fun updateToken(): Observable<String> {
        return service
                .auth(AuthRequest(clientId,
                        clientSecrete,
                        redirectUri,
                        "client_credentials"))
                .map { result ->
                    token = result.access_token
                    token
                }
    }

    fun search(value: String): Observable<PodCast> {
        return service
                .search(value, token)
                .flatMap { result -> Observable.fromIterable(result.results) }
                .map { pc ->
                    PodCast(idApi = pc.id,
                            title = pc.title,
                            description = pc.description,
                            imgFullUrl = pc.image_urls.full,
                            imgThumbUrl = pc.image_urls.thumb)
                }
    }


}