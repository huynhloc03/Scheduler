package com.bignerdranch.android.a4750.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.a4750.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class WeekAdapter(private val daysOfWeek: List<Pair<String, String>>) :
    RecyclerView.Adapter<WeekAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day_week, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(daysOfWeek[position])

        // Add horizontal spacing between items
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.marginEnd = 16  // Add spacing between days
        holder.itemView.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = daysOfWeek.size

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayText: TextView = itemView.findViewById(R.id.dayText)
        private val dayNameText: TextView = itemView.findViewById(R.id.dayNameText)

        fun bind(dayInfo: Pair<String, String>) {
            dayNameText.text = dayInfo.first  // Set the day name
            dayText.text = dayInfo.second  // Set the day number

            // Highlight the current day
            val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
            if (dayInfo.second == today) {
                dayText.setBackgroundResource(R.drawable.current_day_background)
            } else {
                dayText.setBackgroundResource(0)  // Clear background for non-current days
            }

            // Set an OnClickListener for each day
            itemView.setOnClickListener {
                // Here you could open a new screen or dialog to view/add events for the selected day
                Toast.makeText(itemView.context, "Clicked on ${dayInfo.first}, ${dayInfo.second}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
