package com.hlandim.easypod.logic.web.itunes

/**
 * Created by hlandim on 14/11/17.
 */

data class SearchResponse(val resultCount: Int,
                          val results: List<SearchItemResponse>)

data class SearchItemResponse(val collectionId: Long,
                              val artistName: String,
                              val artworkUrl100: String,
                              val artworkUrl30: String,
                              val artworkUrl60: String,
                              val artworkUrl600: String,
                              val collectionName: String,
                              val feedUrl: String,
                              val releaseDate: String)