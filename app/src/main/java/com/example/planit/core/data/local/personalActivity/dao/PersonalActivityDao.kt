package com.example.planit.core.data.local.personalActivity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.planit.core.data.local.personalActivity.entities.PersonalActivity

@Dao
interface PersonalActivityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(personalActivity: PersonalActivity)

    @Query("SELECT * FROM personal_activity")
    fun findAll(): List<PersonalActivity>

}