package ru.progect.rollingmaze.activities

import android.os.Bundle
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