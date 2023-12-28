package com.schwada.reminderapp.ui.reminders_active

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.schwada.reminderapp.ReminderApp
import com.schwada.reminderapp.data.local.reminder.ReminderRepository
import com.schwada.reminderapp.databinding.FragmentRemindersActiveBinding
import com.schwada.reminderapp.ui.main.ReminderAdapter

class RemindersActiveFragment : Fragment() {

    private lateinit var remindersActiveViewModel: RemindersActiveViewModel
    private lateinit var reminderRepository: ReminderRepository
    private var _binding: FragmentRemindersActiveBinding? = null
    private val binding get() = _binding!!

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.top_menu, menu)
//        return true
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRemindersActiveBinding.inflate(inflater, container, false)
        reminderRepository = (activity?.application as ReminderApp).reminderRepository
        remindersActiveViewModel = ViewModelProvider(
            this, RemindersActiveViewModelFactory(reminderRepository)
        ).get(RemindersActiveViewModel::class.java)

        val root: View = binding.root
//        val textView: TextView = binding.textHome
//        remindersActiveViewModel.text.observe(viewLifecycleOwner) { textView.text = it }

        remindersActiveViewModel.activeReminders.observe(viewLifecycleOwner) {
            binding.activeRemindersViewer.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = ReminderAdapter(it, ::onReminderLongPress, ::onReminderPress )
            }
        }

//        remindersActiveViewModel.editMode.observe(viewLifecycleOwner) { isEditMode ->
//            (binding.activeRemindersViewer.adapter as ReminderAdapter).setEditMode(true);
//            updateUIElementsForEditMode(isEditMode)
//        }

        return root
    }

    private fun onReminderLongPress() {
        remindersActiveViewModel.setEditMode(true);
    }

    private fun onReminderPress() {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}