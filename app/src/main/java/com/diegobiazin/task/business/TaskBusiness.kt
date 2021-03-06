package com.diegobiazin.task.business

import android.content.Context
import com.diegobiazin.task.entities.TaskEntity
import com.diegobiazin.task.repository.TaskRepository

class TaskBusiness(context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    fun getList(userId: Int): MutableList<TaskEntity> = mTaskRepository.getList(userId)
}