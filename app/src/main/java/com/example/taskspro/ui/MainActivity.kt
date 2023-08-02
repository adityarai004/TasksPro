package com.example.taskspro.ui

import android.content.Intent
import com.example.taskspro.adapter.RecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskspro.R
import com.example.taskspro.data.Task
import com.example.taskspro.viewmodel.AuthViewModel
import com.example.taskspro.viewmodel.TasksViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var viewModel: TasksViewModel
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var recyclerTasks: RecyclerView
    lateinit var fab:FloatingActionButton
    lateinit var authViewModel: AuthViewModel
    lateinit var logoutBtn:Button
    var x = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.floatingActionButton)
        recyclerTasks = findViewById(R.id.recyclerTasks)
        recyclerTasks.layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        recyclerAdapter = RecyclerAdapter(viewModel, this,authViewModel)
        logoutBtn = findViewById(R.id.logout_btn)
        authViewModel.getCurrentUser()?.let { viewModel.getTasks(it) }
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
                    authViewModel.getCurrentUser()
                        ?.let { it1 -> viewModel.addTask(Task(task = x ), it1) }
                }
                dialog.dismiss()
            }
        }

        logoutBtn.setOnClickListener {
            authViewModel.logOut()
            recyclerAdapter.updateList(emptyList())
            Toast.makeText(this, authViewModel.getCurrentUser()?.email ?: "no user",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        recyclerTasks.adapter = recyclerAdapter

        viewModel.tasksLiveData.observe(this){
            recyclerAdapter.updateList(it)
        }
    }
}