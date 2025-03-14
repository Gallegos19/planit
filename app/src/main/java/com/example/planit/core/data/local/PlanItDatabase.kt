package com.example.planit.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.planit.core.data.local.personalActivity.dao.PersonalActivityDao
import com.example.planit.core.data.local.personalActivity.dao.PersonalActivityInfoDao
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo

@Database(entities = [PersonalActivityInfo::class, PersonalActivity::class], version = 1, exportSchema = false)
abstract class PlanItDatabase: RoomDatabase() {

    abstract fun personalActivityInfoDao(): PersonalActivityInfoDao
    abstract fun personalActivityDao(): PersonalActivityDao

    companion object {
        @Volatile
        private var Instance: PlanItDatabase? = null;

        fun getDatabase(context: Context): PlanItDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, PlanItDatabase::class.java, "planit_ database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Instance = it
                    }
            }
        }
    }

}
