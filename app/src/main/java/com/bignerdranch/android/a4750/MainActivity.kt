package com.bignerdranch.android.a4750

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.a4750.ui.ListFragment
import com.bignerdranch.android.a4750.ui.MonthFragment
import com.bignerdranch.android.a4750.ui.WeekFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set "Month" tab as the default selected item
        bottomNavigationView.selectedItemId = R.id.nav_month

        // Load the "Month" fragment as the default fragment on startup
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MonthFragment())
                .commit()
        }

        // Set up the listener for BottomNavigationView to handle tab selection
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_list -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ListFragment())
                        .commit()
                    true
                }
                R.id.nav_month -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MonthFragment())
                        .commit()
                    true
                }
                R.id.nav_week -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, WeekFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
