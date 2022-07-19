package com.example.to_docompose.repository

import com.example.to_docompose.data.ToDoDAO
import com.example.to_docompose.data.entity.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(
    private val todoDao: ToDoDAO
) {

    fun getTaskById(selectedTaskId: Int) = todoDao.getTaskById(selectedTaskId)

    fun getTaskById(toDoTask: ToDoTask) = todoDao.addTask(toDoTask)

    fun updateTask(updateTask: ToDoTask) = todoDao.updateTask(updateTask)

    fun getAllTasks() = todoDao.getAllTasks()

    fun getSortedByLowPriority() = todoDao.getSortedByLowPriority()

    fun deleteTask(toDoTask: ToDoTask) = todoDao.deleteTask(toDoTask)

    fun deleteAllTasks() = todoDao.deleteAllTasks()

    fun deleteAllTasks(searchQuery : String) = todoDao.search(searchQuery)

}