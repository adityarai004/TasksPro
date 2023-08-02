package com.example.taskspro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskspro.data.Task
import com.example.taskspro.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val firestoreRepository: FirestoreRepository) : ViewModel() {
    var tasksLiveData = MutableLiveData<List<Task>>()

    fun getTasks(user: FirebaseUser){
        tasksLiveData = firestoreRepository.getTasks(user)
    }
    fun addTask(task: Task,user: FirebaseUser){
        firestoreRepository.addTask(task,user)
    }

    fun deleteTask(task: Task,user: FirebaseUser){
        firestoreRepository.deleteTask(task,user)
    }

    fun updateTask(taskOld: Task,taskNew:Task,user: FirebaseUser){
        firestoreRepository.updateTask(taskOld,taskNew,user)
    }

}