package com.example.todoapp_hw16.ui.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.data.model.TodoState
import com.example.todoapp_hw16.databinding.EditFragmentBinding
import com.example.todoapp_hw16.ui.task.TaskViewModel
import java.util.*

class EditFragment(): Fragment(R.layout.edit_fragment), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: EditFragmentBinding
    private val viewModel : TaskViewModel by viewModels()
    var pickedDate: Date? = null
    var idTodo : Int? = null
    var userCreator : String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EditFragmentBinding.bind(view)

        setValue()

        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.btnEditTask.setOnClickListener {
            if (getTodo() != null) {
                viewModel.updateTodo(getTodo()!!)
                Toast.makeText(context, "Edited Task", Toast.LENGTH_SHORT ).show()
                findNavController().navigate(R.id.action_editFragment_to_homeFragment)
            }
            else Toast.makeText(context, "Please Full Items", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        pickedDate  = Date(p1, p2, p3)
        binding.etDate.setText("$p1/$p2/$p3")
    }
    private fun setValue() {
        var getTodoId:Int? = requireArguments().getInt("todo")
        if (getTodoId != null) {
            idTodo = getTodoId
            viewModel.searchById(getTodoId).observe(viewLifecycleOwner){
                binding.etTitle.setText( it.title)
                binding.etDesc.setText( it.description)
                binding.etDate.setText(it.dateTime.toString())
                pickedDate = it.dateTime
                binding.snTodo.setSelection( it.state.ordinal)
                userCreator = it.userCreator
            }
        }
    }
    private fun selectState(state: String): TodoState {
        return when (state) {
            "Todo" -> {
                TodoState.TODO
            }
            "Doing" -> {
                TodoState.DOING
            }
            "Done" -> {
                TodoState.DONE
            }
            else -> {
                TodoState.DONE
            }
        }
    }
    private fun getTodo(): Todo? {
        if (checkInputs()) return null

        val mTitle = binding.etTitle.text.toString().trim()
        val mDescribe = binding.etDesc.text.toString().trim()
        val mState = binding.snTodo.selectedItem.toString()
        val mDate = pickedDate
        return userCreator?.let{Todo(idTodo!!,mTitle, mDescribe, selectState(mState),it,mDate!!)}
    }
    private fun checkInputs(): Boolean {
        return binding.etTitle.check()
                && binding.etDesc.check()
                && pickedDate != null
    }
    private fun EditText.check(): Boolean {
        return !this.text.isNullOrBlank()
    }
}