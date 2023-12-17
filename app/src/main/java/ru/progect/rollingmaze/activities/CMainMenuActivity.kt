package ru.progect.rollingmaze.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.progect.rollingmaze.databinding.ActivityMainBinding

class CMainMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun goToTheGameActivity(view: View){
        val intent = Intent(this, CGameActivity::class.java)
        startActivity(intent)
    }

}