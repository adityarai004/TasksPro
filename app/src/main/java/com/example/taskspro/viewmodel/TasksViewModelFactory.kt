package com.example.taskspro.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.taskspro.repository.TasksRepository

class TasksViewModelFactory(var repository: TasksRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try{
            val constructor = modelClass.getDeclaredConstructor(TasksRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            Log.e("TAG",e.message.toString())
        }

        return super.create(modelClass)
    }
}