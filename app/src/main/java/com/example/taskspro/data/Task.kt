package com.example.taskspro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,
    var task:String,
    var isDone:Boolean = false)