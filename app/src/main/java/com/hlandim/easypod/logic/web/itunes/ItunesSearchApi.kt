package com.hlandim.easypod.logic.web.itunes

import com.hlandim.easypod.domain.PodCast
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hlandim on 14/11/17.
 */
class ItunesSearchApi {

    var service: ItunesSearchService

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val retrofit = Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
        service = retrofit.create(ItunesSearchService::class.java)
    }


    private object Holder {
        val INSTANCE = ItunesSearchApi()
    }

    companion object {
        val instance: ItunesSearchApi by lazy { Holder.INSTANCE }
        val mediaType: String by lazy { "podcast" }
        val entity: String by lazy { "podcast" }
    }

    fun search(value: String): Observable<PodCast> {
        return service
                .search(value, mediaType, entity)
                .flatMap { result -> Observable.fromIterable(result.results) }
                .filter { it.feedUrl != null && it.feedUrl.isNotEmpty() && it.feedUrl.isNotBlank() }
                .map { pc ->
                    PodCast(idApi = pc.collectionId,
                            title = pc.artistName,
                            description = pc.collectionName,
                            imgFullUrl = pc.artworkUrl600,
                            imgThumbUrl = pc.artworkUrl100,
                            feedUrl = pc.feedUrl,
                            signed = false)
                }
    }
}