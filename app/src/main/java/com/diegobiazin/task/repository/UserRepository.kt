package com.diegobiazin.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.diegobiazin.task.constants.DataBaseConstants
import java.lang.Exception

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

    fun isEmailExistent(email: String): Boolean {
        var ret: Boolean = false
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID)

            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ?"

            val selectionArgs = arrayOf(email)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
//        db.rawQuery("select * from user where email = diego", null)
            ret = cursor.count > 0

            cursor.close()
        } catch (e: Exception) {
            throw e
        }

        return ret
    }

}