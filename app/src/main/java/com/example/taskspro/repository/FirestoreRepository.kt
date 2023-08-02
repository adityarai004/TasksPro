package com.example.taskspro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskspro.data.Task
import com.google.firebase.auth.FirebaseUser

interface FirestoreRepository {

    fun addTask(task: Task,user: FirebaseUser)
    fun updateTask(taskOld: Task,taskNew:Task,user: FirebaseUser)
    fun deleteTask(task: Task,user: FirebaseUser)
    fun getTasks(user: FirebaseUser): MutableLiveData<List<Task>>
}