package com.example.taskspro.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskspro.data.Task

@Database(
    entities = [Task::class], version = 1,exportSchema = true
)
abstract class TasksDatabase:RoomDatabase() {
    abstract fun taskDao(): TasksDao

    companion object{

        @Volatile
        private var INSTANCE: TasksDatabase? = null

        fun getDatabase(context: Context): TasksDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    TasksDatabase::class.java,
                    "tasks_table").build()
                INSTANCE = instance

                instance
            }
        }

    }
}