package com.example.todoapp_hw16.ui.task

import android.app.Application
import androidx.lifecycle.*
import com.example.todoapp_hw16.data.database.DatabaseTodo
import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.data.repository.TodoRepository
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {

    private val todoDao = DatabaseTodo.getDatabase(application).todoDao()
    private val repository: TodoRepository = TodoRepository(todoDao)

    fun addTodo(todo: Todo){
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }
    fun removeTodo(todo: Todo){
        viewModelScope.launch {
            repository.removeTodo(todo)
        }
    }
    fun updateTodo(todo: Todo){
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }
    fun searchById(id:Int): LiveData<Todo> {
        return repository.searchById(id).asLiveData()
    }

//    fun searchTodo(keyWord: String): LiveData<List<Todo>> {
//        return repository.searchTodo(keyWord).asLiveData()
//    }
//
//    fun getTodoUserById(id:String): LiveData<List<Todo>> {
//        return repository.getTodoUserById(id).asLiveData()
//    }



}