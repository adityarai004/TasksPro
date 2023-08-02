package com.example.taskspro.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskspro.R
import com.example.taskspro.data.Task
import com.example.taskspro.viewmodel.AuthViewModel
import com.example.taskspro.viewmodel.TasksViewModel

class RecyclerAdapter(private val VM: TasksViewModel, private val context: Context,private val authViewModel: AuthViewModel) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var allTasks = mutableListOf<Task>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskDesc: TextView = itemView.findViewById(R.id.task_desc)
        val isTaskDone: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tasks_recycler_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return allTasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = allTasks[position]
        holder.isTaskDone.isChecked = task.isDone
        holder.taskDesc.text = task.task

        holder.isTaskDone.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            val taskOld = Task(task = task.task, isDone = !isChecked)
            val taskNew = Task(task = task.task, isDone = isChecked)
            authViewModel.getCurrentUser()?.let { VM.updateTask(taskOld,taskNew, it) }
        }
//
        holder.taskDesc.setOnClickListener {
            updateTask(task)
        }

        holder.taskDesc.setOnLongClickListener {
            deleteTask(task)
            true
        }
    }

    private fun deleteTask(task: Task) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmation")
        builder.setMessage("Are you sure you want to proceed?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            authViewModel.getCurrentUser()?.let { VM.deleteTask(task, it) }
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun updateTask(task: Task) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(R.layout.dialog_box)
        val dialog = dialogBuilder.create()
        dialog.show()
        dialog.findViewById<TextView>(R.id.header)!!.text = "Update Task"
        val et = dialog.findViewById<TextView>(R.id.newTask)
        et!!.text = task.task
        dialog.findViewById<TextView>(R.id.doneButton)?.setOnClickListener {
            val taskNew = Task(task.id, et.text.toString(), task.isDone)
            val taskOld = Task(task = task.task, isDone = task.isDone)
            authViewModel.getCurrentUser()?.let { it1 -> VM.updateTask(taskOld,taskNew, it1) }
            dialog.dismiss()
        }
    }

    fun updateList(newList: List<Task>) {
        allTasks.clear()
        allTasks.addAll(newList)
        notifyDataSetChanged()
    }
}
