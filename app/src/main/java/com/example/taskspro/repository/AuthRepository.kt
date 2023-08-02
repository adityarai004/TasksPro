package com.example.taskspro.repository

import com.example.taskspro.Resource
import com.google.firebase.auth.FirebaseUser


interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser>
    fun getCurrentUser(): FirebaseUser?
    fun logout()
}