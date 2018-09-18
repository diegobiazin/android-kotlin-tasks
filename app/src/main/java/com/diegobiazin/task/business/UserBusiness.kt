package com.diegobiazin.task.business

import android.content.Context
import com.diegobiazin.task.repository.UserRepository

class UserBusiness(var context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)

    fun insert(name: String, email: String, password: String) {
        val userId = mUserRepository.insert(name, email, password)
        val str =""
    }

}