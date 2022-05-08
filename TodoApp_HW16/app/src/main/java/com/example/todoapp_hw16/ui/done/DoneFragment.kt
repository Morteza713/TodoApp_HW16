package com.example.todoapp_hw16.ui.done

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.TodoState
import com.example.todoapp_hw16.databinding.DoneFragmentBinding
import com.example.todoapp_hw16.ui.task.TaskViewModel
import com.example.todoapp_hw16.ui.todo.TodoAdapter
import com.example.todoapp_hw16.ui.todo.TodoViewModel

class DoneFragment():Fragment(R.layout.done_fragment) {

    lateinit var binding: DoneFragmentBinding
    private val viewModel: TaskViewModel by viewModels()
    private val todoViewModel: TodoViewModel by activityViewModels()
    lateinit var saveUser: SharedPreferences
    lateinit var todoAdapter: TodoAdapter
    var username: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DoneFragmentBinding.bind(view)
        saveUser = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        todoAdapter = TodoAdapter()
        binding.rvDone.adapter = todoAdapter
        binding.rvDone.layoutManager = LinearLayoutManager(requireContext())
        swipeToDelete(binding.rvDone)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
//                    username?.let { sharedViewModel.getTodoUserById(it) }
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        getUser()
    }
    private fun getData() {
        todoViewModel.getData.observe(viewLifecycleOwner) { data ->
            val filter = data.filter {
                it.state == TodoState.DONE
            }
            if (filter.isEmpty().not()) {
                todoAdapter.setValuesList(filter)
            }
        }
    }
    private fun getUser() {
        saveUser.getString("username", "user")?.let {
            if (it == "admin") {
                todoViewModel.getAllTodo().observe(viewLifecycleOwner) { data ->
                    val filter = data.filter {
                        it.state == TodoState.DONE
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
}
