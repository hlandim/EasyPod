package com.hlandim.easypod.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hlandim.easypod.domain.Episode
import com.hlandim.easypod.domain.PodCast

/**
 * Created by hlandim on 15/11/17.
 */
@Database(entities = arrayOf(PodCast::class, Episode::class),
        version = 1,
        exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun podCastDao(): PodCastDao
    abstract fun episodeDao(): EpisodeDao
}