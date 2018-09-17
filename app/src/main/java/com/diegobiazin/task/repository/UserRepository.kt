package com.diegobiazin.task.repository

import android.content.ContentValues
import android.content.Context
import com.diegobiazin.task.constants.DataBaseConstants

class UserRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context);

    companion object {
        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null)
                INSTANCE = UserRepository(context)

            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null
    }

    fun insert(name: String, email: String, password: String): Int {
        val db = mTaskDataBaseHelper.writableDatabase

        val insertValue = ContentValues()
        insertValue.put(DataBaseConstants.USER.COLUMNS.NAME, name)
        insertValue.put(DataBaseConstants.USER.COLUMNS.EMAIL, email)
        insertValue.put(DataBaseConstants.USER.COLUMNS.PASSWORD, password)

        return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValue).toInt()
    }

}