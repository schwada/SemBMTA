package com.schwada.reminderapp.ui.reminders_active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.data.local.reminder.ReminderRepository
import kotlinx.coroutines.launch

class RemindersActiveViewModel(private val reminderRepository: ReminderRepository) : ViewModel() {

    val activeReminders: LiveData<List<Reminder>> = reminderRepository.activeReminders.asLiveData()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _editMode = MutableLiveData<Boolean>()
    val editMode: LiveData<Boolean> get() = _editMode

    fun setEditMode(isEditMode: Boolean) {
        _editMode.value = isEditMode
    }

    fun deleteReminders() = viewModelScope.launch{
        val reminders = activeReminders.value?.filter { it.isSelected }
        for (reminder in reminders!!) {
            reminderRepository.deleteReminder(reminder)
        }
        setEditMode(false)
    }

}

class RemindersActiveViewModelFactory(private val reminderRepository: ReminderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemindersActiveViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RemindersActiveViewModel(reminderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}