package ru.data

import ru.data.models.GameScore
import ru.data.apis.DefaultApi

public const val BASE_URL = "https://shiny-space-fiesta-ppw9xv6g6w92rw7q-8080.app.github.dev/"    // Сервер с API

/**
 * Метод возвращает максимальный результат пользователя username
 */
fun getUserRank(username: String): String {
    var rank = ""
    try {
        val api = DefaultApi(BASE_URL)
        rank = api.userRankGet(username).userRank.toString()
        rank += " место"
    } catch (e: Exception) {
        rank = "--- место"
    }
    return rank
}

/**
 * Метод записывает результат пользователя
 */
fun postUserResult(username: String, diff: Int, score: String) {
    try {
        val api = DefaultApi(BASE_URL)
        val res = GameScore(username, diff, score)
        api.scoresUploadPost(res)
    } catch (e: Exception) {
        return
    }
}
