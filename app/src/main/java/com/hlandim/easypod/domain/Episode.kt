package com.hlandim.easypod.domain

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE
import com.hlandim.easypod.domain.Episode.Companion.TABLE_NAME

/**
 * Created by hlandim on 15/11/17.
 */
@Entity(tableName = TABLE_NAME,
        indices = arrayOf(Index(value = "id_api",
                name = "id_api_episode",
                unique = true)),
        foreignKeys = arrayOf(ForeignKey(entity = PodCast::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("podcast_id"),
                onDelete = CASCADE)))
class Episode(@PrimaryKey(autoGenerate = true) var id: Long = 0,
              @ColumnInfo(name = "id_api") var idApi: Long = 0,
              @ColumnInfo(name = "title") var title: String = "",
              @ColumnInfo(name = "podcast_id") var podCastId: Long,
              @ColumnInfo(name = "description") var description: String?,
              @ColumnInfo(name = "mime_type") var mimeType: String,
              @ColumnInfo(name = "url") var url: String
) {

    constructor() : this(id = 0,
            idApi = 0,
            title = "",
            podCastId = 0,
            description = "",
            mimeType = "",
            url = "")

    companion object {
        const val TABLE_NAME: String = "Episode"
    }
}