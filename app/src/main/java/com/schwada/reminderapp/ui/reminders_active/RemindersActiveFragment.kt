package com.schwada.reminderapp.ui.reminders_active

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.schwada.reminderapp.R
import com.schwada.reminderapp.ReminderApp
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.data.local.reminder.ReminderRepository
import com.schwada.reminderapp.databinding.FragmentRemindersActiveBinding
import com.schwada.reminderapp.ui.main.ReminderAdapter

class RemindersActiveFragment : Fragment() {

    private lateinit var remindersActiveViewModel: RemindersActiveViewModel
    private lateinit var reminderRepository: ReminderRepository
    private var _binding: FragmentRemindersActiveBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmenu: Menu

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() { remindersActiveViewModel.setEditMode(false) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_menu, menu)
                menu.findItem(R.id.action_close).isVisible = remindersActiveViewModel.editMode.value == true;
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_close -> {
                        remindersActiveViewModel.setEditMode(false)
                        menuItem.isVisible = false;
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRemindersActiveBinding.inflate(inflater, container, false)
        val root: View = binding.root
        reminderRepository = (activity?.application as ReminderApp).reminderRepository
        remindersActiveViewModel = ViewModelProvider(
            this, RemindersActiveViewModelFactory(reminderRepository)
        ).get(RemindersActiveViewModel::class.java)


        remindersActiveViewModel.activeReminders.observe(viewLifecycleOwner) {
            binding.activeRemindersViewer.apply {
                layoutManager = LinearLayoutManager(this.context)
                adapter = ReminderAdapter(it, ::onReminderLongPress, ::onReminderPress )
            }
        }

        remindersActiveViewModel.editMode.observe(viewLifecycleOwner) { isEditMode ->
            (binding.activeRemindersViewer.adapter as ReminderAdapter).setEditMode(isEditMode);
            activity?.findViewById<Button>(R.id.btn_createnew)?.visibility = if(isEditMode) View.GONE else View.VISIBLE
            val navigationView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
            val deletebtn = navigationView?.menu?.findItem(R.id.navigation_delete)
            deletebtn?.isVisible = isEditMode;
            deletebtn?.setOnMenuItemClickListener {
                remindersActiveViewModel.deleteReminders()
                true
            }

            navigationView?.menu?.findItem(R.id.navigation_dashboard)?.isVisible = !isEditMode;
            navigationView?.menu?.findItem(R.id.navigation_home)?.isVisible = !isEditMode;
            activity?.invalidateOptionsMenu()
            callback.isEnabled = isEditMode;
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return root
    }




    private fun onReminderLongPress() {
        remindersActiveViewModel.setEditMode(true);
    }

    private fun onReminderPress(reminder: Reminder) {
        val navController = activity?.findNavController(R.id.activity_fragment)
        val bundle = Bundle()
        bundle.putLong("reminderId", reminder.id)
        bundle.putString("reminderTitle", reminder.title)
        bundle.putString("reminderDesc", reminder.description)
        bundle.putBoolean("reminderNotify", reminder.notifyAlarm)
        bundle.putLong("reminderDate", reminder.date)
        navController?.navigate(R.id.createReminderActivity, bundle);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}