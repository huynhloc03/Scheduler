package com.bignerdranch.android.a4750.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.a4750.R
import com.bignerdranch.android.a4750.adapter.TaskAdapter
import com.bignerdranch.android.a4750.databinding.FragmentListBinding
import com.bignerdranch.android.a4750.model.TaskItem
import com.bignerdranch.android.a4750.viewmodel.SharedViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView with click listener
        taskAdapter = TaskAdapter(mutableListOf()) { selectedTask ->
            navigateToTaskDetailFragment(selectedTask) // Pass the selected task
        }
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.taskRecyclerView.adapter = taskAdapter

        // Observe task list changes
        sharedViewModel.taskList.observe(viewLifecycleOwner) { updatedList ->
            taskAdapter.updateTasks(updatedList)
        }

        // Handle FAB click to navigate to AddTaskFragment
        binding.fabAddTask.setOnClickListener {
            navigateToAddTaskFragment()
        }
    }

    private fun navigateToAddTaskFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AddTaskFragment())
            .addToBackStack(null)
            .commit()
    }



    private fun navigateToTaskDetailFragment(task: TaskItem) {
        binding.taskRecyclerView.visibility = View.GONE // Hide RecyclerView
        binding.fabAddTask.visibility = View.GONE // Hide FAB if necessary

        val taskDetailFragment = TaskDetailFragment(task)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, taskDetailFragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
