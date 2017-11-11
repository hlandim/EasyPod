package com.hlandim.easypod.logic.web.audiosear
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by hlandim on 09/11/17.
 */
interface AudioSearService {


    @GET("api/search/shows/{value}")
    fun search(@Path("value") value: String, @Query("access_token") token: String): Observable<PodCastSearchResponse>

    @POST("oauth/token")
    fun auth(@Body body: AuthRequest): Observable<AuthResponse>
}