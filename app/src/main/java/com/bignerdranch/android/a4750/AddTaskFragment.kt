package com.bignerdranch.android.a4750.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bignerdranch.android.a4750.databinding.FragmentAddTaskBinding
import com.bignerdranch.android.a4750.model.TaskItem
import com.bignerdranch.android.a4750.viewmodel.SharedViewModel
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.util.Locale

class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var selectedColor: Int = Color.WHITE // Default background color

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle Start Date click
        binding.startDate.setOnClickListener {
            showDatePicker { date ->
                binding.startDate.text = date
            }
        }

        // Handle Start Time click
        binding.startTime.setOnClickListener {
            showTimePicker { time ->
                binding.startTime.text = time
            }
        }

        // Handle Color Picker button
        binding.colorPickerButton.setOnClickListener {
            showColorPicker()
        }



        // Apply the behavior to EditText fields
        setupEditTextKeyBehavior(binding.taskTitle)
        setupEditTextKeyBehavior(binding.taskLocation)
        setupEditTextKeyBehavior(binding.taskNotes)
        setupEditTextKeyBehavior(binding.tagEditText)

        // Add Task
        binding.btnAdd.setOnClickListener {
            saveTask()
        }

        // Go Back
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun saveTask() {
        val title = binding.taskTitle.text.toString()
        val startDate = binding.startDate.text.toString()
        val startTime = binding.startTime.text.toString()
        val tag = binding.tagEditText.text.toString()
        val notes = binding.taskNotes.text.toString()
        val location = binding.taskLocation.text.toString()

        if (title.isEmpty() || startDate.isEmpty() || startTime.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show()
        } else {
            val newTask = TaskItem(title, "$startDate $startTime", tag, selectedColor, notes, location  )
            sharedViewModel.addTask(newTask)

            // Debug: Log the task list to verify the task is added
            sharedViewModel.taskList.value?.forEach {
                println("Task in SharedViewModel: ${it.title} - ${it.dueDate}")
            }

            Toast.makeText(requireContext(), "Task Added", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH)
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val datePickerDialog = android.app.DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedMonth + 1, selectedDay, selectedYear)
            onDateSelected(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val calendar = java.util.Calendar.getInstance()
        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)

        val timePickerDialog = android.app.TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val formattedTime = String.format(
                Locale.getDefault(),
                "%02d:%02d %s",
                if (selectedHour % 12 == 0) 12 else selectedHour % 12,
                selectedMinute,
                if (selectedHour >= 12) "PM" else "AM"
            )
            onTimeSelected(formattedTime)
        }, hour, minute, false)

        timePickerDialog.show()
    }

    private fun showColorPicker() {
        ColorPickerDialog.Builder(requireContext())
            .setTitle("Choose Color")
            .setPreferenceName("ColorPickerDialog")
            .setPositiveButton("Select", ColorEnvelopeListener { envelope, _ ->
                // Update selected color
                selectedColor = envelope.color

                // Set the button background color to the selected color
                binding.colorPickerButton.setBackgroundColor(selectedColor)
                binding.colorPickerButton.text = ""

            })
            .setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }
            .attachAlphaSlideBar(true) // Optional
            .attachBrightnessSlideBar(true) // Optional
            .setBottomSpace(12) // Optional
            .show()
    }

    private fun setupEditTextKeyBehavior(editText: EditText) {
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            ) {
                closeKeyboard()
                v.clearFocus()
                true
            } else {
                false
            }
        }

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                closeKeyboard()
            }
        }
    }

    private fun closeKeyboard() {
        val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        val currentFocus = requireActivity().currentFocus
        currentFocus?.let {
            inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
