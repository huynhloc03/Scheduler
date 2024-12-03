package com.bignerdranch.android.a4750.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.a4750.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MonthAdapter : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    private val monthsList = generateMonths() // Generate all 12 months

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val month = monthsList[position]
        holder.bind(month)
    }

    override fun getItemCount(): Int = monthsList.size

    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val monthNameText: TextView = itemView.findViewById(R.id.monthNameText)
        private val daysRecyclerView: RecyclerView = itemView.findViewById(R.id.daysRecyclerView)

        fun bind(calendar: Calendar) {
            // Display the month name
            val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.time)
            monthNameText.text = monthName

            // Generate days for the current month with a sample list of event days
            val daysInMonth: List<String> = generateDaysOfMonth(calendar)
            val eventDays = listOf("5", "13", "20") // Example event days

            // Set up the RecyclerView for days in the month
            daysRecyclerView.layoutManager = GridLayoutManager(itemView.context, 7)
            daysRecyclerView.isNestedScrollingEnabled = false // Disable nested scrolling
            daysRecyclerView.adapter = CalendarAdapter(daysInMonth, eventDays)
        }
    }

    private fun generateMonths(): List<Calendar> {
        val months = mutableListOf<Calendar>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to the first day of each month

        // Generate all 12 months for the current year
        for (month in 0..11) {
            val monthCalendar = Calendar.getInstance()
            monthCalendar.set(Calendar.MONTH, month)
            months.add(monthCalendar)
        }
        return months
    }

    private fun generateDaysOfMonth(calendar: Calendar): List<String> {
        val days = mutableListOf<String>()
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        // Fill empty slots for days of the previous month if needed
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        for (i in 0 until firstDayOfWeek) {
            days.add("") // Empty slots for previous month
        }

        // Populate days of the current month
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..daysInMonth) {
            days.add(i.toString())
        }

        return days
    }
}
