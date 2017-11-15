package com.hlandim.easypod.logic.web.itunes

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by hlandim on 14/11/17.
 */
interface ItunesSearchService {

    @GET("search")
    fun search(@Query("term") query: String, @Query("mediaType") mediaType: String, @Query("entity") entity: String): Observable<SearchResponse>
}