package com.schwada.reminderapp.ui.reminders_archived

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.schwada.reminderapp.ReminderApp
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.data.local.reminder.ReminderRepository
import com.schwada.reminderapp.databinding.FragmentRemindersArchivedBinding
import com.schwada.reminderapp.ui.main.ReminderAdapter

class RemindersArchivedFragment : Fragment() {

    private lateinit var remindersArchivedViewModel: RemindersArchivedViewModel
    private lateinit var reminderRepository: ReminderRepository
    private var _binding: FragmentRemindersArchivedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
        reminderRepository = (activity?.application as ReminderApp).reminderRepository
        remindersArchivedViewModel = ViewModelProvider(
            this, RemindersArchivedViewModelFactory(reminderRepository)
        ).get(RemindersArchivedViewModel::class.java)
        _binding = FragmentRemindersArchivedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        remindersArchivedViewModel.archivedReminders.observe(viewLifecycleOwner) {
            binding.archivedRemindersViewer.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = ReminderAdapter(it, ::onReminderLongPress, ::onReminderPress )
            }
        }
        return root
    }

    private fun onReminderLongPress() {
    }

    private fun onReminderPress(reminder: Reminder) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}