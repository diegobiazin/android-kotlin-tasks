package com.diegobiazin.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.diegobiazin.task.constants.DataBaseConstants
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

    private val createTablePriority = """ CREATE TABLE ${DataBaseConstants.PRIORITY.TABLE_NAME} (
        ${DataBaseConstants.PRIORITY.COLUMNS.ID} INTEGER PRIMARY KEY,
        ${DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION} TEXT
        );"""

    private val createTableTask = """ CREATE TABLE ${DataBaseConstants.TASK.TABLE_NAME} (
        ${DataBaseConstants.TASK.COLUMNS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.TASK.COLUMNS.USERID} INTEGER,
        ${DataBaseConstants.TASK.COLUMNS.PRIORITYID} INTEGER,
        ${DataBaseConstants.TASK.COLUMNS.DESCRIPTION} TEXT,
        ${DataBaseConstants.TASK.COLUMNS.COMPLETE} TEXT,
        ${DataBaseConstants.TASK.COLUMNS.DUEDATE} TEXT
        );"""

    // Populando dados de prioridades
    private val insertPriorities = ("INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME}"
            + " values (1, 'Baixa'), (2, 'Média'), (3, 'Alta'), (4, 'Crítica')")

    private val deleteTableUser = "drop table if exists ${USER.TABLE_NAME}"
    private val deleteTablePriority = "drop table if exists ${DataBaseConstants.PRIORITY.TABLE_NAME}"
    private val deleteTableTask = "drop table if exists ${DataBaseConstants.TASK.TABLE_NAME}"

    override fun onCreate(sqlLite: SQLiteDatabase) {
        // Faz a criação das tabelas
        sqlLite.execSQL(createTableUser)
        sqlLite.execSQL(createTablePriority)
        sqlLite.execSQL(createTableTask)

        // Faz a inserção das categorias
        sqlLite.execSQL(insertPriorities)
    }

    override fun onUpgrade(sqlLite: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Remoção
        sqlLite.execSQL(deleteTableUser)
        sqlLite.execSQL(deleteTablePriority)
        sqlLite.execSQL(deleteTableTask)

        // Criação
        sqlLite.execSQL(createTableUser)
        sqlLite.execSQL(createTablePriority)
        sqlLite.execSQL(createTableTask)
    }


}