package com.example.taskspro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.taskspro.R
import com.example.taskspro.data.Task
import com.example.taskspro.viewmodel.TasksViewModel
import kotlinx.coroutines.launch


class RecyclerAdapter(private val VM: TasksViewModel, private val context: Context):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val allTasks = mutableListOf<Task>()
    lateinit var task: Task
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val taskDesc:TextView = itemView.findViewById(R.id.task_desc)
        val isTaskDone:CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tasks_recycler_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return allTasks.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tasksViewModel = allTasks[position]
        task = tasksViewModel
        holder.isTaskDone.isChecked = tasksViewModel.isDone

        holder.taskDesc.text = tasksViewModel.task
        holder.isTaskDone.setOnClickListener {
            VM.viewModelScope.launch (Dispatchers.IO){
                task.isDone = !task.isDone
                VM.updateTask(task)
            }
        }

        holder.taskDesc.setOnClickListener {
            updateTask()
        }

        holder.taskDesc.setOnLongClickListener {
            deleteTask()
            true
        }
    }

    private fun deleteTask() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to proceed?")

        builder.setPositiveButton("Yes") { dialog, which ->
            VM.viewModelScope.launch (Dispatchers.IO){
                VM.deleteTask(task)
                dialog.dismiss()
            }
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateTask() {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(R.layout.dialog_box)
        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.findViewById<TextView>(R.id.header)!!.text = "Update Task"
        val et =dialog.findViewById<EditText>(R.id.newTask)
        et!!.setText(task.task)
        dialog.findViewById<Button>(R.id.doneButton)?.setOnClickListener {
            val x = dialog.findViewById<EditText>(R.id.newTask)?.text.toString()
            task.task = x
            VM.viewModelScope.launch(Dispatchers.IO){
                VM.updateTask(task)
            }
            dialog.dismiss()
        }
    }

    fun updateList(newList: List<Task>) {
        allTasks.clear()
        allTasks.addAll(newList)
        notifyDataSetChanged()
    }
}