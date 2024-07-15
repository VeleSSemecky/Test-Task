package com.example.test.ui.feature.test

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.test.app.R
import com.example.test.app.databinding.FragmentTestBinding
import com.example.test.core.base.fragment.BaseFragment
import com.example.test.core.extensions.viewBinding
import com.example.test.worker.BootEventWorker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class TestFragment : BaseFragment(R.layout.fragment_test) {

    private val binding by viewBinding(FragmentTestBinding::bind)

    private val viewModel: TestViewModel by viewModels { viewModelFactory }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted
            schedulePeriodicWork()
        } else {
            // Permission denied
            Toast.makeText(requireContext(), "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.flowBootEvents.collectWithLifecycle { events ->
            if (events.isEmpty()) {
                binding.tvBootEvents.text = "No boots detected"
            } else {
                val eventsGroupedByDay = events.groupBy { event ->
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(event.date))
                }

                val displayText = eventsGroupedByDay.entries.joinToString("\n") { (date, eventsOnDate) ->
                    "$date - ${eventsOnDate.size}"
                }

                binding.tvBootEvents.text = displayText
            }
        }

        binding.btnSaveSettings.setOnClickListener {
            val dismissalsAllowed = binding.etDismissalsAllowed.text.toString().toIntOrNull() ?: 5
            val intervalBetweenDismissals = binding.etIntervalBetweenDismissals.text.toString().toIntOrNull() ?: 20

            viewModel.saveSettings(dismissalsAllowed, intervalBetweenDismissals)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                schedulePeriodicWork()
            }
        } else {
            schedulePeriodicWork()
        }
    }

    private fun schedulePeriodicWork() {
        val workRequest = PeriodicWorkRequestBuilder<BootEventWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "BootEventWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
