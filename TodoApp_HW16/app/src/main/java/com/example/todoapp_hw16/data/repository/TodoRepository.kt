package com.example.todoapp_hw16.data.repository

import com.example.todoapp_hw16.data.model.Todo
import com.example.todoapp_hw16.data.model.dao.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    fun searchTodo(keyWord: String): Flow<List<Todo>> {
        return todoDao.searchTodo(keyWord)
    }
    fun searchById(todoId: Int) : Flow<Todo> {
        return todoDao.searchById(todoId)
    }
    fun getTodoUserById(id:String): Flow<List<Todo>> {
        return todoDao.getTodoUserById(id)
    }
    fun getAllTodo(): Flow<List<Todo>> {
        return todoDao.getAllTodo()
    }


    suspend fun addTodo(todo: Todo){
        todoDao.addTodo(todo)
    }
    suspend fun updateTodo(todo: Todo) {
        todoDao.editTodo(todo)
    }
    suspend fun removeAllTodo(){
        todoDao.removeAll()
    }
    suspend fun removeTodo(todo: Todo) {
        todoDao.removeTodo(todo)
    }
}