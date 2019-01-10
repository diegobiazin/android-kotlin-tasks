package com.diegobiazin.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.diegobiazin.task.constants.DataBaseConstants
import com.diegobiazin.task.entities.UserEntity
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

    fun get(email: String, password: String): UserEntity? {
        var userEntity: UserEntity? = null
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID,
                    DataBaseConstants.USER.COLUMNS.NAME,
                    DataBaseConstants.USER.COLUMNS.EMAIL,
                    DataBaseConstants.USER.COLUMNS.PASSWORD)

            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMNS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()

                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME))
                val email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL))

                //Preenchendo a entidade de usuário
                userEntity = UserEntity(userId, name, email)
            }

            cursor.close()
        } catch (e: Exception) {
            return userEntity
        }

        return userEntity
    }

}