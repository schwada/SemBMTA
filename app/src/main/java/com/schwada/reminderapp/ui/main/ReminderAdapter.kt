package com.schwada.reminderapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.schwada.reminderapp.R
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.databinding.CellReminderBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale


class ReminderAdapter(
    private val items: List<Reminder>,
    private val onLongClickListener: () -> Unit,
    private val onItemClicked: () -> Unit,
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var isEditMode: Boolean = false


    fun setEditMode(editMode: Boolean) {
        isEditMode = editMode
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val binding = CellReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bindToView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun toggleSelection(position: Int) {
//        items[position].isSelected = !items[position].isSelected
//        notifyItemChanged(position)
        onLongClickListener()
    }


    inner class ReminderViewHolder(
        private val context: Context,
        private val binding: CellReminderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindToView(reminder: Reminder) {
            val date = Date(reminder.date);
            binding.viewReminderName.text = reminder.title
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeformat = SimpleDateFormat("HH:mm", Locale.getDefault())
            binding.viewReminderDate.text = dateFormat.format(date);
            binding.viewReminderTime.text = timeformat.format(date);

            if (reminder.notifyAlarm) {
                binding.viewIsNotify.setImageResource(R.drawable.baseline_alarm_24)
            } else {
                binding.viewIsNotify.setImageResource(R.drawable.ic_notifications_black_24dp)
            }

            binding.viewIsSelected.isChecked = reminder.isSelected;

            binding.reminderCellContainer.setOnClickListener {
                toggleSelection(adapterPosition)
            }
            binding.reminderCellContainer.setOnLongClickListener {
                // Handle long press to enter edit mode
                toggleSelection(adapterPosition)
                true
            }
        }


    }
}

