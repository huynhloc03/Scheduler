package com.bignerdranch.android.a4750.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.a4750.model.TaskItem

class SharedViewModel : ViewModel() {

    // LiveData to store the list of tasks
    val taskList = MutableLiveData<MutableList<TaskItem>>(mutableListOf())

    // Method to add a new task to the list
    fun addTask(task: TaskItem) {
        Log.d("SharedViewModel", "Adding task: ${task.title} - ${task.dueDate}")

        // Add the task to the current list
        val currentList = taskList.value ?: mutableListOf()
        currentList.add(task)
        Log.d("SharedViewModel", "Task list size after addition: ${currentList.size}")

        // Trigger observers by updating the value
        taskList.value = currentList

        // Log the entire task list for debugging
        Log.d("SharedViewModel", "Current task list contents:")
        currentList.forEachIndexed { index, item ->
            Log.d("SharedViewModel", "Task $index: ${item.title} - ${item.dueDate}")
        }
    }
}
