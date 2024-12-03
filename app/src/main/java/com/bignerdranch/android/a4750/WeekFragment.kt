package com.bignerdranch.android.a4750.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bignerdranch.android.a4750.R
import java.text.SimpleDateFormat
import java.util.*

class WeekFragment : Fragment() {

    private lateinit var timeColumn: LinearLayout
    private lateinit var daysHeader: LinearLayout
    private lateinit var weekDateRange: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_week, container, false)

        timeColumn = view.findViewById(R.id.timeColumn)
        daysHeader = view.findViewById(R.id.daysHeader)
        weekDateRange = view.findViewById(R.id.weekDateRange)

        setupTimeColumn()
        setupDaysHeader()

        Log.d("Debug", "Time Column Children Count: ${timeColumn.childCount}")
        Log.d("Debug", "Days Header Children Count: ${daysHeader.childCount}")
        setupDateRange()

        return view
    }

    private fun setupTimeColumn() {
        timeColumn.removeAllViews()
        val timeFormat = SimpleDateFormat("h a", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 1) // Start at 1 AM
        calendar.set(Calendar.MINUTE, 0)

        // Populate time slots for the entire day (1 AM to 12 AM)
        for (i in 1..24) {
            val timeTextView = TextView(requireContext()).apply {
                text = timeFormat.format(calendar.time)
                textSize = 14f
                setPadding(4, 20, 4, 20)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            timeColumn.addView(timeTextView)
            calendar.add(Calendar.HOUR_OF_DAY, 1) // Increment by 1 hour
        }
    }



    private fun setupDaysHeader() {
        daysHeader.removeAllViews()
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dateFormat = SimpleDateFormat("d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

        for (i in 0..6) {
            val dayView = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                gravity = android.view.Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            val dayName = TextView(requireContext()).apply {
                text = dayFormat.format(calendar.time)
                textSize = 16f
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }

            val dayDate = TextView(requireContext()).apply {
                text = dateFormat.format(calendar.time)
                textSize = 14f
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }

            dayView.addView(dayName)
            dayView.addView(dayDate)
            daysHeader.addView(dayView)

            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    private fun setupDateRange() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)

        val startOfWeek = SimpleDateFormat("d MMM", Locale.getDefault()).format(calendar.time)
        calendar.add(Calendar.DAY_OF_MONTH, 6) // Move to the end of the week
        val endOfWeek = SimpleDateFormat("d MMM", Locale.getDefault()).format(calendar.time)

        weekDateRange.text = "$startOfWeek - $endOfWeek"
    }
}
