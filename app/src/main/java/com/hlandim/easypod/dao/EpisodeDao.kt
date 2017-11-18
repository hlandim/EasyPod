package com.hlandim.easypod.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.hlandim.easypod.domain.Episode

/**
 * Created by hlandim on 15/11/17.
 */
@Dao
interface EpisodeDao {

    @Query("SELECT * FROM ${Episode.TABLE_NAME}")
    fun getAll(): List<Episode>

    @Query("SELECT * FROM ${Episode.TABLE_NAME} WHERE id = :arg0 LIMIT 1")
    fun getById(id: Long): Episode?

    @Query("SELECT * FROM ${Episode.TABLE_NAME} WHERE id_api = :arg0 LIMIT 1")
    fun getByApiId(id: Long): Episode?

    @Query("SELECT * FROM ${Episode.TABLE_NAME} WHERE title = :arg0 LIMIT 1")
    fun getByTitle(title: String): Episode?

    @Query("SELECT * FROM ${Episode.TABLE_NAME} WHERE podcast_id = :arg0")
    fun getByPodCast(podCastId: Long): List<Episode>

    @Insert(onConflict = REPLACE)
    fun insert(vararg episode: Episode)

    @Delete
    fun delete(episode: Episode)

    @Query("DELETE FROM ${Episode.TABLE_NAME} where podcast_id = :arg0")
    fun deleteAllFromPodCast(podCastId: Long)
}