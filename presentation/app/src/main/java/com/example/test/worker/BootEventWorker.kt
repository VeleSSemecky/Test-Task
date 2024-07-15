package com.example.test.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.test.app.R
import com.test.data.datasource.database.dao.BootEventDao
import com.test.data.datasource.sharedprefs.BootEventPersistent
import dagger.android.HasAndroidInjector
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

class BootEventWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var bootEventDao: BootEventDao

    @Inject
    lateinit var bootEventPersistent: BootEventPersistent


    init {
        (applicationContext as HasAndroidInjector).androidInjector().inject(this)
        Log.i("BootReceiver", "BootEventWorker")
    }

    override suspend fun doWork(): Result {

        val dismissalsAllowed = bootEventPersistent.getDismissalsAllowed()
        val intervalBetweenDismissals = bootEventPersistent.getIntervalBetweenDismissals()
        val dismissalCount = bootEventPersistent.getDismissalCount() + 1

        val bootEvents = bootEventDao.getAll()
        // Determine the notification content based on boot events
        val notificationContent = when {
            bootEvents.isEmpty() -> "No boots detected"
            bootEvents.size == 1 -> {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
                "The boot was detected = ${sdf.format(Date(bootEvents[0].date))}"
            }
            else -> {
                val lastBoot = bootEvents[0]
                val preLastBoot = bootEvents[1]
                val delta = lastBoot.date - preLastBoot.date
                "Last boots time delta = ${delta / 1000} seconds"
            }
        }

        // Show notification
        showNotification(notificationContent, dismissalCount, dismissalsAllowed, intervalBetweenDismissals)

        return Result.success()
    }

    private fun showNotification(content: String, dismissalCount: Int, dismissalsAllowed: Int, intervalBetweenDismissals: Int) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "boot_channel",
                "Boot Events",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val dismissIntent = Intent(applicationContext, NotificationDismissReceiver::class.java)
        val dismissPendingIntent = PendingIntent.getBroadcast(
            applicationContext, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "boot_channel")
            .setContentTitle("Boot Event")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDeleteIntent(dismissPendingIntent)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}
