package com.test.data.datasource.sharedprefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootEventPersistent @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveDismissalsAllowed(dismissalsAllowed: Int) {
        sharedPreferences.edit().putInt(DISMISSALS_ALLOWED, dismissalsAllowed).apply()
    }

    fun saveIntervalBetweenDismissals(intervalBetweenDismissals: Int) {
        sharedPreferences.edit().putInt(INTERVAL_BETWEEN_DISMISSALS, intervalBetweenDismissals).apply()
    }

    fun saveDismissalCount(dismissalCount: Int) {
        sharedPreferences.edit().putInt(DISMISSAL_COUNT, dismissalCount).apply()
    }

    fun getDismissalsAllowed() = sharedPreferences.getInt(DISMISSALS_ALLOWED, 5)

    fun getIntervalBetweenDismissals() = sharedPreferences.getInt(INTERVAL_BETWEEN_DISMISSALS, 20)

    fun getDismissalCount() = sharedPreferences.getInt(DISMISSAL_COUNT, 0)

    companion object {

        private const val DISMISSALS_ALLOWED = "dismissals_allowed"
        private const val INTERVAL_BETWEEN_DISMISSALS = "interval_between_dismissals"
        private const val DISMISSAL_COUNT = "dismissal_count"
    }
}
