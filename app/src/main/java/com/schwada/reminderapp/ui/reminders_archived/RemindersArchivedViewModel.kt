package com.schwada.reminderapp.ui.reminders_archived

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.data.local.reminder.ReminderRepository

class RemindersArchivedViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    val archivedReminders: LiveData<List<Reminder>> = reminderRepository.archivedReminders.asLiveData()
}

class RemindersArchivedViewModelFactory(private val reminderRepository: ReminderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersArchivedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RemindersArchivedViewModel(reminderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}