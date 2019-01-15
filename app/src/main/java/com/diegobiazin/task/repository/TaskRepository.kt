package com.diegobiazin.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.diegobiazin.task.constants.DataBaseConstants
import com.diegobiazin.task.entities.PriorityEntity
import com.diegobiazin.task.entities.TaskEntity
import com.diegobiazin.task.entities.UserEntity

class TaskRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null)
                INSTANCE = TaskRepository(context)

            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null
    }

    // CRUD
    fun get(id: Int): TaskEntity? {
        var taskEntity: TaskEntity? = null
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.TASK.COLUMNS.ID,
                    DataBaseConstants.TASK.COLUMNS.USERID,
                    DataBaseConstants.TASK.COLUMNS.PRIORITYID,
                    DataBaseConstants.TASK.COLUMNS.DESCRIPTION,
                    DataBaseConstants.TASK.COLUMNS.DUEDATE,
                    DataBaseConstants.TASK.COLUMNS.COMPLETE)

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(id.toString())

            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if (cursor.count > 0) {
                cursor.moveToFirst()

                val taskId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID)) == 1)

                //Preenchendo a entidade de usuário
                taskEntity = TaskEntity(taskId, userId, priorityId, description, dueDate, complete)
            }

            cursor.close()
        } catch (e: java.lang.Exception) {
            return taskEntity
        }

        return taskEntity
    }

    fun getList(userId: Int): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME}" +
                    "WHERE ${DataBaseConstants.TASK.COLUMNS.USERID} = $userId", null)

            if (cursor.count > 0) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                    val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                    list.add(TaskEntity(id, userId, priorityId, description, dueDate, complete))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }

    fun insert(task: TaskEntity) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val complete: Int = if (task.complete) 1 else 0

            val insertValue = ContentValues()
            insertValue.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userId)
            insertValue.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            insertValue.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValue.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            insertValue.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValue)
        } catch (e: Exception) {
            throw e
        }
    }

    fun update(task: TaskEntity) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val complete: Int = if (task.complete) 1 else 0

            val updateValue = ContentValues()
            updateValue.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userId)
            updateValue.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            updateValue.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValue.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueDate)
            updateValue.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            val selection = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val selectionArgs = arrayOf(task.id.toString())

            db.update(DataBaseConstants.TASK.TABLE_NAME, updateValue, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(taskId: Int) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val where = "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            val whereArgs = arrayOf(taskId.toString())

            db.delete(DataBaseConstants.TASK.TABLE_NAME, where, whereArgs)
        } catch (e: Exception) {
            throw e
        }
    }
}