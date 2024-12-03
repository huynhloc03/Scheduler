package com.bignerdranch.android.a4750.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.a4750.R
import java.util.Calendar

class CalendarAdapter(
    private val daysOfMonth: List<String>,  // List of days to display
    private val eventDays: List<String>     // List of days with events
) : RecyclerView.Adapter<CalendarAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = daysOfMonth[position]
        holder.bind(day)
    }

    override fun getItemCount(): Int = daysOfMonth.size

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayText: TextView = itemView.findViewById(R.id.dayText)
        private val eventIndicator: View = itemView.findViewById(R.id.eventIndicator)

        fun bind(day: String) {
            // Set the day text
            dayText.text = day

            // Highlight the current day
            val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
            if (day == currentDay) {
                dayText.setBackgroundResource(R.drawable.current_day_background)
            } else {
                dayText.setBackgroundResource(0)  // Remove background for non-current days
            }

            // Show or hide the event indicator
            eventIndicator.visibility = if (eventDays.contains(day)) View.VISIBLE else View.GONE
        }
    }
}
