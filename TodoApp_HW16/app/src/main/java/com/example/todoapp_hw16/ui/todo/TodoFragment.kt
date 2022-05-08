package com.example.todoapp_hw16.ui.todo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.TodoState
import com.example.todoapp_hw16.databinding.TodoFragmentBinding
import com.example.todoapp_hw16.ui.task.TaskViewModel

class TodoFragment():Fragment(R.layout.todo_fragment) {

    lateinit var binding: TodoFragmentBinding
    private val viewModel: TaskViewModel by viewModels()
    private val todoViewModel: TodoViewModel by activityViewModels()
    lateinit var saveUser: SharedPreferences
    var todoAdapter: TodoAdapter = TodoAdapter()
    var username: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TodoFragmentBinding.bind(view)
        saveUser = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        with(binding.rvTodo) {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        swipeToDelete(binding.rvTodo)

    }
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = todoAdapter.todoList[viewHolder.adapterPosition]
                viewModel.removeTodo(deletedItem)
                todoAdapter.notifyItemRemoved(viewHolder.adapterPosition)
//                username?.let { todoViewModel.getTodoUserById(it) }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun getUser() {
        saveUser.getString("username", "user")?.let {
            if (it == "admin") {
                todoViewModel.getAllTodo().observe(viewLifecycleOwner) { data ->
                    val filter = data.filter {
                        it.state == TodoState.TODO
                    }
                    if (filter.isEmpty().not()) {
                        todoAdapter.setValuesList(filter)
                    }
                }
            } else {
                if (username != it) {
                    username = it
                    todoViewModel.getTodoUserById(it)
                }
                getData()
            }
        }
    }
    private fun getData() {
        todoViewModel.getData.observe(viewLifecycleOwner) { data ->
            val filter = data.filter {
                it.state == TodoState.TODO
            }
            if (filter.isEmpty().not()) {
                todoAdapter.setValuesList(filter)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        getUser()
    }

}