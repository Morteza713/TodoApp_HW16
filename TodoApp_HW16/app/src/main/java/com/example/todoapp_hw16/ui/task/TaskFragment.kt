package com.example.todoapp_hw16.ui.task

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp_hw16.R
import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.data.model.TodoState
import com.example.todoapp_hw16.databinding.TaskFargmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class TaskFragment:Fragment(R.layout.task_fargment), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: TaskFargmentBinding
    private val viewModel : TaskViewModel by viewModels()
    var addDate: Date? = null
    var imgUri: Uri? = null
    lateinit var saveUser: SharedPreferences
    val pictureLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()){
        Handler(Looper.getMainLooper()).post {
            binding.imgAddPic.setImageURI(it)
            imgUri = it
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TaskFargmentBinding.bind(view)
        saveUser = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)

        binding.imgAddPic.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).
            setTitle("Choose image").
            setMessage("Choose your way").
            setNeutralButton("Gallery"){dialog,which ->
                pictureLauncher.launch(arrayOf("image/*" ))
            }.setNegativeButton("cancel"){
                    dialog,which ->
            }.show()
        }
        binding.edDate.setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            DatePickerDialog(
                requireContext(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.btnAddTask.setOnClickListener {
            if (getTodo() != null) {
                viewModel.addTodo(getTodo()!!)
                Toast.makeText(context, "Added Task !",Toast.LENGTH_SHORT ).show()
                findNavController().navigate(R.id.action_taskFragment_to_homeFragment)
            }
            else Toast.makeText(context, "Please Full Items",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        addDate  = Date(p1, p2, p3)
        binding.edDate.setText("$p1/$p2/$p3")
    }
    private fun getTodo(): Todo? {
        if (!checkInputs()) return null

        val mTitle = binding.etTitle.text.toString().trim()
        val mDescribe = binding.edDesc.text.toString().trim()
        val mState = binding.snTodo.selectedItem.toString()
        val mUser = saveUser.getString("username", "user")
        val mDate = addDate
        val imgUriString : String? = if (imgUri == null)  null
        else  imgUri.toString()
        return Todo(0, mTitle, mDescribe, selectState(mState), mUser!!,mDate!!, imgUriString)
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
    private fun checkInputs(): Boolean {
        return binding.etTitle.check()
                && binding.edDesc.check()
                && addDate != null
    }
    private fun EditText.check(): Boolean {
        return !this.text.isNullOrBlank()
    }

}