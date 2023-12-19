package ru.progect.rollingmaze.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.progect.rollingmaze.databinding.ActivityGameBinding
import ru.progect.rollingmaze.game.GameHandler

class CGameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGameBinding
    private lateinit var gameHandler: GameHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)

        gameHandler = GameHandler(this, binding.gameSurface)

        setContentView(binding.root)
        gameHandler.start()
    }

    override fun onResume() {
        super.onResume()
        gameHandler.start()
    }

    override fun onPause() {
        super.onPause()
        gameHandler.pause()
    }
}