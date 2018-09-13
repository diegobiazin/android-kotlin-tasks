package com.diegobiazin.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.diegobiazin.task.constants.DataBaseConstants.USER

class TaskDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION: Int = 1
        private val DATABASE_NAME: String = "task.db"
    }

    // SQLite
    // INTEGER, REAL, TEXT, BLOB

    private val createTableUser = """ CREATE TABLE ${USER.TABLE_NAME} (
        ${USER.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${USER.COLUMNS.NAME} TEXT,
        ${USER.COLUMNS.EMAIL} TEXT,
        ${USER.COLUMNS.PASSWORD} TEXT
        );"""

    private val deleteTableUser = "drop table if exists ${USER.TABLE_NAME}"

    override fun onCreate(sqlLite: SQLiteDatabase) {
        sqlLite.execSQL(createTableUser)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        sqlLite.execSQL(deleteTableUser)
        sqlLite.execSQL(createTableUser)
    }


}