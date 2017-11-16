package com.hlandim.easypod.domain

import android.arch.persistence.room.*
import com.hlandim.easypod.domain.PodCast.Companion.TABLE_NAME

/**
 * Created by hlandim on 08/11/17.
 */

@Entity(tableName = TABLE_NAME, indices = arrayOf(Index(value = "id_api", name = "id_api_podcast", unique = true)))
class PodCast(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(name = "id_api") var idApi: Long = 0,
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "description") var description: String = "",
        @ColumnInfo(name = "img_full_url") var imgFullUrl: String = "",
        @ColumnInfo(name = "img_thumb_url") var imgThumbUrl: String = "",
        @ColumnInfo(name = "feed_url") var feedUrl: String = "",
        @Ignore var signed: Boolean = false
) {

    constructor() : this(id = 0,
            idApi = 0,
            title = "",
            description = "",
            imgFullUrl = "",
            imgThumbUrl = "",
            feedUrl = "",
            signed = false)

    companion object {
        const val TABLE_NAME: String = "Podcast"
    }
}