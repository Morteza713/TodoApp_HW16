package com.example.todoapp_hw16.ui.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.databinding.TodoFragmentBinding
import com.example.todoapp_hw16.databinding.TodoItemBinding

class TodoAdapter():RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var todoList = mutableListOf<Todo>()

    inner class TodoViewHolder(private val binding: TodoItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.tvTitle.text = todoList[position].title
            binding.tvDecs
            binding.tvDecs.text = todoList[position].description
            binding.root.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_homeFragment_to_editFragment,
                    bundleOf("todo" to todoList[position].id)
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
    fun setValuesList(todoData: List<Todo>) {
        val todoDiffUtil = TodoDiffUtil(todoList, todoData)
        val todoDiffResults = DiffUtil.calculateDiff(todoDiffUtil)
        this.todoList = todoData as MutableList<Todo>
        todoDiffResults.dispatchUpdatesTo(this)
        notifyItemInserted(todoData.size)
    }
}