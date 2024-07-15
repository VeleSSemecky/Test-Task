package com.example.test.di.module

import android.content.Context
import androidx.room.Room
import com.example.test.app.BuildConfig
import com.test.data.datasource.database.AppDatabase
import com.test.data.datasource.database.dao.BootEventDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    private const val DATABASE_NAME = "AppDatabase.db"

    private fun Context.createDB() =
        Room.databaseBuilder(this, AppDatabase::class.java, DATABASE_NAME)
            .apply {
                if (BuildConfig.DEBUG) fallbackToDestructiveMigration()
            }
            .addMigrations(*emptyArray())
            .build()

    @Provides
    @Singleton
    fun provideAppDatabase(
        context: Context
    ): AppDatabase {
        val database = context.createDB()
        if (BuildConfig.DEBUG) {
            try {
                database.inTransaction()
            } catch (e: IllegalStateException) {
                context.deleteDatabase(DATABASE_NAME)
                return context.createDB()
            }
        }
        return database
    }

    @Provides
    fun provideBootEventDao(database: AppDatabase): BootEventDao = database.getBootEventDao()
}
