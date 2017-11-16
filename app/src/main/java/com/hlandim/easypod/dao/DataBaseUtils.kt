package com.hlandim.easypod.dao

import android.arch.persistence.room.Room
import android.content.Context

/**
 * Created by hlandim on 15/11/17.
 */
class DataBaseUtils {

    companion object {
        val dataBaseName: String = "easypod-db"
        fun getAppDataBase(context: Context): AppDataBase {
            return Room.databaseBuilder(context,
                    AppDataBase::class.java, "easypod-db")
                    .allowMainThreadQueries()
                    .build()
        }
    }


}