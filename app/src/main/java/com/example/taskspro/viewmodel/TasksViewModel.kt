package com.example.taskspro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskspro.data.Task
import com.example.taskspro.repository.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class TasksViewModel(var repository: TasksRepository):ViewModel() {

     fun getAllTasks():LiveData<List<Task>>  = repository.allTasks()

    suspend fun deleteTask(task : Task) = viewModelScope.launch (Dispatchers.IO){
        repository.deleteTask(task)
    }


    suspend fun insertTask(task : Task) =viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(task)
    }


    suspend fun updateTask(task : Task) = viewModelScope.launch (Dispatchers.IO){
        repository.updateTask(task)
    }

}