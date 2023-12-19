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

    /**
     * Инициализируем активность
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextUserId = findViewById(R.id.editTextUserId)
        // Если текст был сохранен, восстановим его
        username = intent.getStringExtra("username")?.toString() ?: ""

        editTextUserId.setText(username)
    }

    /**
     * Сохраняем текст из поля ввода для имения пользователя
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Сохраняем текст в переменную при уничтожении активности
        outState.putString("username", editTextUserId.text.toString())
    }

    /**
     * Метод для перехода на активность с игрой
     */
    fun goToTheGameActivity(view: View){
        val intent = Intent(this, CGameActivity::class.java)
        intent.putExtra("username", editTextUserId.text.toString())
        startActivity(intent)
    }

    /**
     * Метод для перехода на активность с таблицей рекордов
     */
    fun goToTheRecordTableActivity(view: View){
        val intent = Intent(this, CRecordTableActivity::class.java)
        intent.putExtra("username", editTextUserId.text.toString())
        startActivity(intent)
    }
}