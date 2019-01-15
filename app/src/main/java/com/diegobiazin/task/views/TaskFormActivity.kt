package com.diegobiazin.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.diegobiazin.task.R
import com.diegobiazin.task.business.PriorityBusiness
import kotlinx.android.synthetic.main.activity_task_form.*

class TaskFormActivity : AppCompatActivity() {

    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        loadPriorities()
    }

    private fun loadPriorities() {
        val lstPriorityEntity = mPriorityBusiness.getList()
        val lstPriorities =lstPriorityEntity.map { it.description }

        val adapter =ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)
        spinnerPriority.adapter = adapter
    }
}
