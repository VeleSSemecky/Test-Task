package com.example.test.ui.feature.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.data.datasource.database.dao.BootEventDao
import com.test.data.datasource.database.entity.BootEventDbo
import com.test.data.datasource.sharedprefs.BootEventPersistent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestViewModel @Inject constructor(
    private val bootEventDao: BootEventDao,
    private val bootEventPersistent: BootEventPersistent
) : ViewModel() {

    private val _flowBootEvents = MutableStateFlow<List<BootEventDbo>>(emptyList())
    val flowBootEvents: StateFlow<List<BootEventDbo>>
        get() = _flowBootEvents.asStateFlow()


    fun onCreate() = viewModelScope.launch {
        bootEventDao.getFlowAll().collect { bootEvents ->
            _flowBootEvents.emit(bootEvents)
        }
    }

    fun onBackClicked() {
    }

    fun saveSettings(dismissalsAllowed: Int, intervalBetweenDismissals: Int) {
        bootEventPersistent.saveDismissalsAllowed(dismissalsAllowed)
        bootEventPersistent.saveIntervalBetweenDismissals(intervalBetweenDismissals)
    }
}
