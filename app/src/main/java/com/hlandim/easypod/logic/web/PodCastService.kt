package com.hlandim.easypod.logic.web

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by hlandim on 09/11/17.
 */
interface PodCastService {


    @GET("api/search/shows/{value}")
    fun search(@Path("value") value: String, @Query("access_token") token: String): Observable<PodCastSearchReponse>
}