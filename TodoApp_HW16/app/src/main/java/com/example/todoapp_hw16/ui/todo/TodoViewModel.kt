package com.example.todoapp_hw16.ui.todo

import android.app.Application
import androidx.lifecycle.*
import com.example.todoapp_hw16.data.database.DatabaseTodo
import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TodoViewModel(application: Application): AndroidViewModel(application){

    private val todoDao = DatabaseTodo.getDatabase(application).todoDao()

    var getData: LiveData<List<Todo>> = MutableLiveData(emptyList())
    private val repository: TodoRepository = TodoRepository(todoDao)

    fun removeAllTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeAllTodo()
        }
    }

    fun getAllTodo() : LiveData<List<Todo>> {
        return repository.getAllTodo().asLiveData()
    }

    fun getTodoUserById(id:String) {
        getData =  repository.getTodoUserById(id).asLiveData()
    }



}