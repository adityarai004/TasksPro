package com.example.taskspro.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskspro.data.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepositoryImpl @Inject constructor(private val firebaseFirestore: FirebaseFirestore) : FirestoreRepository {

    override fun addTask(task: Task,user: FirebaseUser) {
        val usersCollection = firebaseFirestore.collection("users")
        val userDoc = usersCollection.document(user.uid)

        val taskData = hashMapOf(
            "taskString" to task.task,
            "isDone" to task.isDone
        )

        userDoc.collection("tasks")
            .add(taskData)
            .addOnSuccessListener { documentReference ->
                val taskId = documentReference.id
                task.id = taskId
            }
            .addOnFailureListener { exception ->
            }
    }

    override fun deleteTask(task: Task,user: FirebaseUser) {
        val userCollection = firebaseFirestore.collection("users")
        val userDoc = userCollection.document(user.uid)
        val taskDocRef = userDoc.collection("tasks").document(task.id)
        taskDocRef.delete()
    }

    override fun updateTask(taskOld: Task, taskNew: Task,user: FirebaseUser) {
        val userCollection = firebaseFirestore.collection("users")
        val userDoc = userCollection.document(user.uid)
        val taskDocRef = userDoc.collection("tasks")
        taskDocRef.whereEqualTo("taskString",taskOld.task)
            .whereEqualTo("isDone",taskOld.isDone)
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    val document = it.documents[0]
                    document.reference.update("isDone",taskNew.isDone)
                    document.reference.update("taskString",taskNew.task)
                }
            }
    }


    override fun getTasks(user: FirebaseUser): MutableLiveData<List<Task>> {
        val tasksLiveData = MutableLiveData<List<Task>>()

        val usersCollection = firebaseFirestore.collection("users")
        val userDoc = usersCollection.document(user.uid)

        userDoc.collection("tasks")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val taskList = mutableListOf<Task>()
                querySnapshot?.let { snapshot ->
                    for (document in snapshot.documents) {
                        val taskId = document.id
                        val taskData = document.data
                        val task = Task(
                            id = taskId,
                            task = taskData?.get("taskString") as String,
                            isDone = taskData["isDone"] as Boolean
                        )
                        taskList.add(task)
                    }
                }

                tasksLiveData.value = taskList
            }

        return tasksLiveData
    }

}