package com.diegobiazin.task.repository

import android.content.Context
import android.database.Cursor
import com.diegobiazin.task.constants.DataBaseConstants
import com.diegobiazin.task.entities.PriorityEntity

class PriorityRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    companion object {
        fun getInstance(context: Context): PriorityRepository {
            if (INSTANCE == null)
                INSTANCE = PriorityRepository(context)

            return INSTANCE as PriorityRepository
        }

        private var INSTANCE: PriorityRepository? = null
    }

    fun getList(): MutableList<PriorityEntity> {
        val list = mutableListOf<PriorityEntity>()
        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.PRIORITY.TABLE_NAME}", null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION))

                    list.add(PriorityEntity(id, description))
                }
            }

            cursor.close()
        } catch (e: Exception) {
            return list
        }

        return list
    }
}