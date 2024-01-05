package com.schwada.reminderapp.ui.reminders_archived

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.schwada.reminderapp.databinding.FragmentRemindersArchivedBinding

class RemindersArchivedFragment : Fragment() {

    private var _binding: FragmentRemindersArchivedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {
//        val remindersArchivedViewModel = ViewModelProvider(this).get(RemindersArchivedViewModel::class.java)

        _binding = FragmentRemindersArchivedBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}