package com.example.test.di.module

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.test.app.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
object PersistenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        if (BuildConfig.DEBUG) {
            context.getSharedPreferences("${BuildConfig.FLAVOR}_preferences", Context.MODE_PRIVATE)
        } else {
            EncryptedSharedPreferences.create(
                "${BuildConfig.FLAVOR}_preferences",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

    @Provides
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver
}
