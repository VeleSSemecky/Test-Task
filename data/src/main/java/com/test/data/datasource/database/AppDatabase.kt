package com.test.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.data.datasource.database.dao.BootEventDao
import com.test.data.datasource.database.entity.BootEventDbo

@Database(
    entities = [BootEventDbo::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBootEventDao(): BootEventDao
}
