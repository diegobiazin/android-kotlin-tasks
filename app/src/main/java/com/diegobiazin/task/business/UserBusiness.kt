package com.diegobiazin.task.business

import android.content.Context
import com.diegobiazin.task.R
import com.diegobiazin.task.constants.TaskConstants
import com.diegobiazin.task.repository.UserRepository
import com.diegobiazin.task.util.SecurityPreferences
import com.diegobiazin.task.util.ValidationException
import java.lang.Exception

class UserBusiness(var context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun insert(name: String, email: String, password: String) {

        try {
            if (name == "" || email == "" || password == "")
                throw ValidationException(context.getString(R.string.informe_todos_campos))

            if (mUserRepository.isEmailExistent(email))
                throw ValidationException(context.getString(R.string.email_em_uso))

            val userId = mUserRepository.insert(name, email, password)

            // Salvar dados do usuário na shared
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)
        } catch (e: Exception) {
            throw e
        }
    }

}