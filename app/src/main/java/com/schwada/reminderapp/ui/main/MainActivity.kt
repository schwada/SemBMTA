package com.schwada.reminderapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.schwada.reminderapp.R
import com.schwada.reminderapp.databinding.ActivityMainBinding
import com.schwada.reminderapp.ui.create.CreateReminderActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.activity_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val createNewButton: Button = findViewById(R.id.btn_createnew)
        createNewButton.setOnClickListener {
            navController.navigate(R.id.createReminderActivity);

        }


    }
}