package ru.progect.rollingmaze.activities

import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.progect.rollingmaze.databinding.ActivityGameBinding
import ru.progect.rollingmaze.game.GameView

class CGameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val size = Point()
        val gameView = GameView(this, size)
        binding.gameContainer.addView(gameView)
        gameView.start()
    }
}