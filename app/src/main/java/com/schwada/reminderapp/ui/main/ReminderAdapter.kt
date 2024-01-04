package com.schwada.reminderapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import com.schwada.reminderapp.R
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.databinding.CellReminderBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ReminderAdapter(
    private val items: List<Reminder>,
    private val onLongClickListener: () -> Unit,
    private val onItemClicked: (reminder: Reminder) -> Unit,
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    private var isEditMode: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    fun setEditMode(editMode: Boolean) {
        isEditMode = editMode
        if(!editMode) for (item in items) item.isSelected = false
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

    fun toggleSelection(position: Int, isChecked: Boolean, reminder: Reminder) {
        if(isEditMode) {
            items[position].isSelected = isChecked
            notifyItemChanged(position)
        } else {
            onItemClicked(reminder)
        }
    }

    fun toggleEditMode(position: Int, isChecked: Boolean) {
        if(!isEditMode) {
            items[position].isSelected = isChecked
            notifyItemChanged(position)
            onLongClickListener()
        }
    }



    inner class ReminderViewHolder(
        private val context: Context,
        private val binding: CellReminderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun bindToView(reminder: Reminder) {
            val date = Date(reminder.date);
            binding.viewReminderName.text = reminder.title
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val timeformat = SimpleDateFormat("HH:mm", Locale.getDefault())
            binding.viewReminderDate.text = dateFormat.format(date);
            binding.viewReminderTime.text = timeformat.format(date);
            binding.viewIsSelected.visibility = if (isEditMode) { View.VISIBLE } else { View.GONE }
            binding.viewIsNotify.setImageResource( if (reminder.notifyAlarm) R.drawable.baseline_alarm_24 else R.drawable.ic_notifications_black_24dp )

            if (!reminder.archived) {
                binding.reminderCellContainer.setOnClickListener {
                    toggleSelection(adapterPosition, !reminder.isSelected, reminder)
                }
                binding.reminderCellContainer.setOnLongClickListener {
                    toggleEditMode(adapterPosition, !reminder.isSelected)
                    true
                }


                binding.viewIsSelected.isChecked = reminder.isSelected;
                binding.reminderCellContainer.setOnTouchListener { view, event ->
                    val card: CardView = view as CardView;
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            card.setContentPadding(0,10,0,10)
                            (card.layoutParams as ViewGroup.MarginLayoutParams).updateMargins(dp(10),dp(5),dp(10),dp(5))
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            card.setContentPadding(1,1,1,1)
                            (card.layoutParams as ViewGroup.MarginLayoutParams).updateMargins(dp(5),dp(5),dp(5),dp(10))
                        }
                    }
                    false
                }
            }
        }


        fun dp(dp: Int): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }

    }
}

