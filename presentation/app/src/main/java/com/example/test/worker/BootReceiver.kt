package com.example.test.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.test.data.datasource.database.dao.BootEventDao
import com.test.data.datasource.database.entity.BootEventDbo
import dagger.android.DaggerBroadcastReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BootReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var bootEventDao: BootEventDao

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.i("BootReceiver", "Intent.ACTION_BOOT_COMPLETED")
            GlobalScope.launch(Dispatchers.IO) {
                bootEventDao.insert(BootEventDbo(date = System.currentTimeMillis()))
            }
            val workRequest = OneTimeWorkRequestBuilder<BootEventWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}