package com.example.taskspro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    var id:String = "",
    var task:String,
    var isDone:Boolean = false){

}