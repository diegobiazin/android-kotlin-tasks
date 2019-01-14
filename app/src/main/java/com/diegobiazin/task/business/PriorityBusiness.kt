package com.diegobiazin.task.business

import android.content.Context
import com.diegobiazin.task.entities.PriorityEntity
import com.diegobiazin.task.repository.PriorityRepository

class PriorityBusiness(context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()
}