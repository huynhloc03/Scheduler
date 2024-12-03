package com.bignerdranch.android.a4750.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bignerdranch.android.a4750.databinding.FragmentTaskDetailBinding
import com.bignerdranch.android.a4750.model.TaskItem

class TaskDetailFragment(private val task: TaskItem) : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind data to the views
        binding.taskDetailTitle.text = task.title
        binding.taskDetailDueDate.text = task.dueDate
        binding.taskDetailTag.text = task.tag
        binding.taskDetailNotes.text = task.notes
        binding.taskDetailLocation.text = task.location


        // Set the background color dynamically
        binding.taskDetailContainer.setBackgroundColor(task.color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
