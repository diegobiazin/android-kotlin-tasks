package com.diegobiazin.task.entities

data class UserEntity(val id: Int, var name: String, var email: String, var password: String = "")