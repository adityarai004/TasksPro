package com.example.taskspro.repository

import androidx.lifecycle.LiveData
import com.example.taskspro.data.Task
import com.example.taskspro.db.TasksDatabase

class TasksRepository(private val tasksDB: TasksDatabase) {
    fun  allTasks(): LiveData<List<Task>> = tasksDB.taskDao().getAllTasks()


     suspend fun insertTask(task : Task){
        tasksDB.taskDao().insertTask(task)
    }
     suspend fun deleteTask(task : Task){
        tasksDB.taskDao().deleteTask(task)
    }
     suspend fun updateTask(task : Task){
        tasksDB.taskDao().updateTask(task)
    }
}