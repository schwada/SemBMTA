package com.schwada.reminderapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.data.local.reminder.ReminderRepository
import kotlinx.coroutines.launch


class CreateReminderViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    fun saveReminder(reminder: Reminder) = viewModelScope.launch {
        reminderRepository.saveReminder(reminder)
    }
}

class CreateReminderViewModelFactory(private val reminderRepository: ReminderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateReminderViewModel(reminderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}