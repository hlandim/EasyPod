package com.hlandim.easypod.logic.web.audiosear

/**
 * Created by hlandim on 10/11/17.
 */
data class AuthResponse(val access_token: String,
                        val token_type: String,
                        val expires_in: Long)

data class PodCastSearchResponse(val query: String,
                                 val total_results: Int,
                                 val page: Int,
                                 val results_per_page: Int,
                                 val results: List<AudioSearPodCast>)

data class AudioSearPodCast(val id: Long,
                            val title: String,
                            val description: String,
                            val image_urls: PodCastUrls)

data class PodCastUrls(val full: String,
                       val thumb: String)