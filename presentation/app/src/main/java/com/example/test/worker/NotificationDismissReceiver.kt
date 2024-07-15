package com.example.test.worker

import android.content.Context
import android.content.Intent
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.test.data.datasource.sharedprefs.BootEventPersistent
import dagger.android.DaggerBroadcastReceiver
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationDismissReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var bootEventPersistent: BootEventPersistent

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val dismissalsAllowed = bootEventPersistent.getDismissalsAllowed()
        val intervalBetweenDismissals = bootEventPersistent.getIntervalBetweenDismissals()
        val dismissalCount = bootEventPersistent.getDismissalCount() + 1

        bootEventPersistent.saveDismissalCount(dismissalCount)

        // Schedule the next notification
        val delay = if (dismissalCount > dismissalsAllowed) 15 * 60 else dismissalCount * intervalBetweenDismissals * 60
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<BootEventWorker>()
            .setInitialDelay(delay.toLong(), TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "BootEventWork",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }
}
