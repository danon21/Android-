package ru.data
data class CUser(
    val uuid: String,
    val name: String
)

data class CGame(
    val id: String,
    val user_id: String,
    val difficulty: Int,
    val score: String
)