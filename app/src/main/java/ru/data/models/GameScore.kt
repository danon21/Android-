/**
 * pg_service 1.0
 * pg_service
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package ru.data.models

import com.squareup.moshi.Json
import ru.data.models.UserName

/**
 * 
 * @param userName 
 * @param difficulty Difficulty of level
 * @param gameScore Time of game
 */
data class GameScore (

    @Json(name = "user_name") val userName: UserName,
    /* Difficulty of level */
    val difficulty: kotlin.Int,
    /* Time of game */
    @Json(name = "game_score") val gameScore: kotlin.String
) {
}
