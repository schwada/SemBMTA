package com.schwada.reminderapp.data.local.reminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "notify_alarm")
    val notifyAlarm: Boolean,
    @ColumnInfo(name = "archived")
    val archived: Boolean,

    var isSelected: Boolean = false
)