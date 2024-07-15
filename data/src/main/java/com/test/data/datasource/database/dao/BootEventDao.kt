package com.test.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.test.data.datasource.database.entity.BootEventDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface BootEventDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(data: BootEventDbo)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(data: List<BootEventDbo>)

    @Query("SELECT * FROM ${BootEventDbo.TABLE_NAME} ORDER BY ${BootEventDbo.DATE} DESC")
    fun getFlowAll(): Flow<List<BootEventDbo>>

    @Query("SELECT * FROM ${BootEventDbo.TABLE_NAME} ORDER BY ${BootEventDbo.DATE} DESC")
    suspend fun getAll(): List<BootEventDbo>
}
