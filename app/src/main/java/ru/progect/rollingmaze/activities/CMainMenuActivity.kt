package ru.progect.rollingmaze.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.progect.rollingmaze.R
import ru.progect.rollingmaze.databinding.ActivityMainBinding

class CMainMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var editTextUserId: EditText
    private var username : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * Инициализируем активность
         */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextUserId = findViewById(R.id.editTextUserId)
        // Если текст был сохранен, восстановим его
        username = intent.getStringExtra("username")?.toString() ?: ""

        editTextUserId.setText(username)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        /**
         * Сохраняем текст из поля ввода для имения пользователя
         */
        super.onSaveInstanceState(outState)
        // Сохраняем текст в переменную при уничтожении активности
        outState.putString("username", editTextUserId.text.toString())
    }

    fun goToTheGameActivity(view: View){
        /**
         * Метод для перехода на активность с игрой
         */
        val intent = Intent(this, CGameActivity::class.java)
        intent.putExtra("username", editTextUserId.text.toString())
        startActivity(intent)
    }

    fun goToTheRecordTableActivity(view: View){
        /**
         * Метод для перехода на активность с таблицей рекордов
         */
        val intent = Intent(this, CRecordTableActivity::class.java)
        intent.putExtra("username", editTextUserId.text.toString())
        startActivity(intent)
    }
}