package com.bignerdranch.android.a4750.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.a4750.R
import com.bignerdranch.android.a4750.model.TaskItem

class TaskAdapter(
    private val taskList: MutableList<TaskItem>,
    private val onTaskClick: (TaskItem) -> Unit // Add a click listener parameter
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleText: TextView = itemView.findViewById(R.id.taskTitleText)
        private val dueDateText: TextView = itemView.findViewById(R.id.taskDueDateText)

        fun bind(task: TaskItem) {
            titleText.text = task.title
            dueDateText.text = task.dueDate

            val background = itemView.context.getDrawable(R.drawable.rounded_item_background)?.mutate()
            if (background != null) {
                DrawableCompat.setTint(background, task.color)
                itemView.background = background
            }

            // Set click listener to pass the task to the fragment
            itemView.setOnClickListener {
                println("Task clicked: ${task.title}")
                onTaskClick(task)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    override fun getItemCount(): Int = taskList.size

    fun updateTasks(newTasks: List<TaskItem>) {
        taskList.clear()
        taskList.addAll(newTasks)
        notifyDataSetChanged()
    }
}
