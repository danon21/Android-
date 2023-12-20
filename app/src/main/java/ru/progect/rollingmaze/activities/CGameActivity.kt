package ru.progect.rollingmaze.activities

import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.progect.rollingmaze.databinding.ActivityGameBinding
import ru.progect.rollingmaze.game.GameHandler


class CGameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private lateinit var gameHandler: GameHandler
    private var username : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        gameHandler = GameHandler(this, binding.gameSurface)

        username = intent.getStringExtra("username").toString()

        setContentView(binding.root)

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
//        Log.wtf("width", binding.gameSurface.width.toString())

        gameHandler = GameHandler(this, resources, binding.gameSurface, Size(metrics.widthPixels, metrics.heightPixels))
        gameHandler.start()
    }

    fun onPauseResume(view: View) {
        if (gameHandler.isRunning) {
            super.onPause()
            gameHandler.pause()
        } else {
            super.onResume()
            gameHandler.start()
        }
    }

    fun onBack(view: View) {

    }
}