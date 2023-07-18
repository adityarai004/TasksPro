package com.example.taskspro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskspro.R
import com.example.taskspro.adapter.RecyclerAdapter
import com.example.taskspro.data.Task
import com.example.taskspro.db.TasksDatabase
import com.example.taskspro.repository.TasksRepository
import com.example.taskspro.viewmodel.TasksViewModel
import com.example.taskspro.viewmodel.TasksViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TasksViewModel
    private lateinit var tasksDatabase: TasksDatabase
    private lateinit var repository: TasksRepository
    private lateinit var factory: TasksViewModelFactory
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var recyclerTasks: RecyclerView
    lateinit var fab:FloatingActionButton
    var x = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tasksDatabase = TasksDatabase.getDatabase(this)
        repository = TasksRepository(tasksDatabase)
        factory = TasksViewModelFactory(repository)
        fab = findViewById(R.id.floatingActionButton)
        recyclerTasks = findViewById(R.id.recyclerTasks)
        recyclerTasks.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        viewModel = ViewModelProvider(this, factory)[TasksViewModel::class.java]


        recyclerAdapter = RecyclerAdapter(viewModel, this)
        fab.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Add A New Task")
            dialogBuilder.setView(R.layout.dialog_box)
            val dialog = dialogBuilder.create()
            dialog.show()
            dialog.findViewById<Button>(R.id.doneButton)?.setOnClickListener {
                Toast.makeText(this,dialog.findViewById<EditText>(R.id.newTask)?.text.toString(),Toast.LENGTH_SHORT).show()
                x = dialog.findViewById<EditText>(R.id.newTask)?.text.toString()
                lifecycleScope.launch(Dispatchers.IO){
                    viewModel.insertTask(Task(task = x))
                }
                dialog.dismiss()
            }
        }

        recyclerTasks.adapter = recyclerAdapter
        viewModel.getAllTasks().observe(this, Observer {
            recyclerAdapter.updateList(it)
        })
    }
}