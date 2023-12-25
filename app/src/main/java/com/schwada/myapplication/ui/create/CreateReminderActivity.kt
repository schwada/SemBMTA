package com.schwada.myapplication.ui.create

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.schwada.myapplication.R;
import androidx.lifecycle.ViewModelProvider

class CreateReminderActivity : AppCompatActivity() {

    private lateinit var createReminderViewModel: CreateReminderViewModel

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.top_menu, menu)
//        return true
//    }

    lateinit var pickTimeBtn: TextView;
    lateinit var pickDateBtn: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reminder)

        // Set up ViewModel
        createReminderViewModel = ViewModelProvider(this).get(CreateReminderViewModel::class.java);

        pickTimeBtn = findViewById(R.id.editTextTime);
        pickTimeBtn.setOnClickListener {
            val c = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute -> pickTimeBtn.setText("$hourOfDay:$minute") },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                false
            );
            timePickerDialog.show()
        }

        pickDateBtn = findViewById(R.id.editTextDate);
        pickDateBtn.setOnClickListener {
            val c = Calendar.getInstance();
            val datePickerDialog = DatePickerDialog(
                this,
                { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    pickDateBtn.setText(String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth))
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                )
            datePickerDialog.show()
        }






        // Set up UI components and listeners
//        val titleEditText: EditText = findViewById(R.id.editTextTitle)
//        val descriptionEditText: EditText = findViewById(R.id.editTextDescription)
        val saveButton: Button = findViewById(R.id.btnSaveReminder);

        saveButton.setOnClickListener {
//            val title = titleEditText.text.toString().trim()
//            val description = descriptionEditText.text.toString().trim()

//            if (title.isNotEmpty() && description.isNotEmpty()) {
//                val newReminder = Reminder(title, description)
//                createReminderViewModel.saveReminder(newReminder)
//
//                // Optionally, you can navigate back to the main screen or perform other actions.
                finish()
//            } else {
//                // Handle empty fields
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//            }
        }
    }

 }
