package com.example.taskspro.di

import com.example.taskspro.repository.AuthRepository
import com.example.taskspro.repository.AuthRepositoryImpl
import com.example.taskspro.repository.FirestoreRepository
import com.example.taskspro.repository.FirestoreRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirestore():FirebaseFirestore =FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreRepository(impl: FirestoreRepositoryImpl):FirestoreRepository = impl
    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl):AuthRepository = impl
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}