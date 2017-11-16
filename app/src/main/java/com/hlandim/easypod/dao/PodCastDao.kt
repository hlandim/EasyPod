package com.hlandim.easypod.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.hlandim.easypod.domain.PodCast

/**
 * Created by hlandim on 15/11/17.
 */
@Dao
interface PodCastDao {

    @Query("SELECT * FROM ${PodCast.TABLE_NAME}")
    fun getAll(): List<PodCast>

    @Query("SELECT * FROM ${PodCast.TABLE_NAME} WHERE id = :arg0 LIMIT 1")
    fun getById(id: Long): PodCast?

    @Query("SELECT * FROM ${PodCast.TABLE_NAME} WHERE id_api = :arg0 LIMIT 1")
    fun getByApiId(id: Long): PodCast?

    @Insert
    fun insert(vararg podCast: PodCast)

    @Delete
    fun delete(podCast: PodCast)
}