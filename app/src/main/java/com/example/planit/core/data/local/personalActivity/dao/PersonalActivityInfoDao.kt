package com.example.planit.core.data.local.personalActivity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivityInfo
import com.example.planit.core.data.local.personalActivity.relations.PersonalActivityWithInfo


@Dao
interface PersonalActivityInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(personalActivityInfo: PersonalActivityInfo): Long

    @Transaction()
    @Query("SELECT * FROM personal_activity_info WHERE id = :id")
    fun findOne(id: Int): PersonalActivityWithInfo

}