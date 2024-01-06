package com.schwada.reminderapp.ui.create

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.schwada.reminderapp.R
import androidx.lifecycle.ViewModelProvider
import com.schwada.reminderapp.ReminderApp
import com.schwada.reminderapp.data.local.reminder.Reminder
import com.schwada.reminderapp.data.local.reminder.ReminderRepository
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import java.util.Locale

class CreateReminderActivity : AppCompatActivity() {

    private lateinit var createReminderViewModel: CreateReminderViewModel
    private lateinit var reminderRepository: ReminderRepository

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.top_menu, menu)
//        return true
//    }



    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reminder)
        reminderRepository = (application as ReminderApp).reminderRepository
        createReminderViewModel = ViewModelProvider(
            this, CreateReminderViewModelFactory(reminderRepository)
        ).get(CreateReminderViewModel::class.java)


        val pickTimeBtn: TextView = findViewById(R.id.timeTextField)
        pickTimeBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this,
                { _, hourOfDay, minute -> pickTimeBtn.text = String.format("%02d:%02d", hourOfDay, minute) },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }

        val pickDateBtn: TextView = findViewById(R.id.dateTextField)
        pickDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int -> pickDateBtn.text = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth) },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
            )
            datePickerDialog.show()
        }

        val nameTextField: EditText = findViewById(R.id.nameTextField)
        val descTextField: EditText = findViewById(R.id.descTextField)
        val saveButton: Button = findViewById(R.id.btnSaveReminder)
        var selectedRadioButton: RadioButton? = null
        val radioGroup: RadioGroup = findViewById(R.id.notifyRadioGroup)
        radioGroup.setOnCheckedChangeListener { group, checkedId -> selectedRadioButton = findViewById(checkedId)}

        val editedReminder: Long = intent.getLongExtra("reminderId", -1)
        if(editedReminder.toInt() != -1) {
            nameTextField.setText(intent.getStringExtra("reminderTitle"))
            descTextField.setText(intent.getStringExtra("reminderDesc"))
            val date = Date(intent.getLongExtra("reminderDate", -1))
            pickDateBtn.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date))
            pickTimeBtn.setText(SimpleDateFormat("HH:mm", Locale.getDefault()).format(date))
            radioGroup.check(if(intent.getBooleanExtra("reminderNotify", true)) R.id.notifyAlarmRadio else R.id.notifyNofiticationRadio)
        }

        saveButton.setOnClickListener {

            val title = nameTextField.text.toString().trim()
            val description = descTextField.text.toString().trim()
            val date = try { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(pickDateBtn.text.toString() + " " + pickTimeBtn.text.toString())}catch (e: Exception) {null}

            // validation
            if(title.isEmpty()) { nameTextField.error = getString(R.string.ReminderFieldMissing); return@setOnClickListener; }
            if(description.isEmpty()) { descTextField.error = getString(R.string.ReminderFieldMissing); return@setOnClickListener; }
            if(selectedRadioButton == null) {
                Toast.makeText(this, getString(R.string.ReminderRadioNofify) + " " + getString(R.string.ReminderFieldMissing), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(date == null) { Toast.makeText(this,getString(R.string.ReminderErrorDateMissing), Toast.LENGTH_SHORT).show(); return@setOnClickListener; }
            if(date.time < System.currentTimeMillis()) { Toast.makeText(this,getString(R.string.ReminderErrorDatePast), Toast.LENGTH_SHORT).show(); return@setOnClickListener; }

            Log.i("CreateReminderActivity", "Passed validation")
            val newReminder = Reminder(
                title = title,
                description = description,
                date = date.time,
                notifyAlarm = selectedRadioButton?.text == "Alarm",
                archived = false
            )

            if(editedReminder.toInt() != -1)  newReminder.id = editedReminder
            createReminderViewModel.saveReminder(newReminder)


            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(this, AlarmReceiver::class.java).apply {
                putExtra("reminderTitle", "testingk")
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                (ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    this,
                    newReminder.hashCode(),
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )

            finish()
        }

    }

 }
