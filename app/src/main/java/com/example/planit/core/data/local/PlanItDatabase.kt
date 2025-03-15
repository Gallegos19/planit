package com.example.planit.core.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.planit.core.data.local.personalActivity.dao.PersonalActivityDao
import com.example.planit.core.data.local.personalActivity.dao.PersonalActivityInfoDao
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo
import kotlin.reflect.typeOf

@Database(entities = [PersonalActivityInfo::class, PersonalActivity::class], version = 1, exportSchema = false)
abstract class PlanItDatabase: RoomDatabase() {

    abstract fun personalActivityInfoDao(): PersonalActivityInfoDao
    abstract fun personalActivityDao(): PersonalActivityDao

    companion object {
        @Volatile
        private var Instance: PlanItDatabase? = null;

        fun getDatabase(context: Context): PlanItDatabase {

            Log.d("Creacion de la bd", "getDatabase: Se utilizo esta funcion ")

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PlanItDatabase::class.java, "planit_ database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Log.d("Database", "getDatabase: ${it}")
                        Instance = it
                    }
            }
        }
    }

}
