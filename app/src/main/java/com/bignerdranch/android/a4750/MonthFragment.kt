package com.bignerdranch.android.a4750.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.a4750.R
import com.bignerdranch.android.a4750.adapter.MonthAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class MonthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        val calendarRecyclerView = view.findViewById<RecyclerView>(R.id.calendarRecyclerView)
        val fabAddEvent = view.findViewById<FloatingActionButton>(R.id.fabAddEvent)

        // Set up the RecyclerView with a vertical LinearLayoutManager
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        calendarRecyclerView.layoutManager = layoutManager
        val adapter = MonthAdapter()
        calendarRecyclerView.adapter = adapter

        // Scroll to the current month
        val currentMonthPosition = Calendar.getInstance().get(Calendar.MONTH) // January = 0, December = 11
        calendarRecyclerView.scrollToPosition(currentMonthPosition)

        // Set click listener for the Floating Action Button to add events
        fabAddEvent.setOnClickListener {
            // Action for adding an event
        }

        return view
    }
}
