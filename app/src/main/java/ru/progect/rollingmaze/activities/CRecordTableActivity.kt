package ru.progect.rollingmaze.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.progect.rollingmaze.R
import ru.progect.rollingmaze.databinding.ActivityRecordTableBinding
import ru.progect.rollingmaze.table.CTableAdapter
import ru.progect.rollingmaze.table.CTableManager

class CRecordTableActivity : AppCompatActivity()  {
    private lateinit var binding : ActivityRecordTableBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var tableManager: CTableManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        tableManager = CTableManager(CTableManager.getData())
        recyclerView.adapter = CTableAdapter(tableManager)
    }

    override fun onBackPressed() {
        val intent = Intent(this, CMainMenuActivity::class.java)
        startActivity(intent)
    }

    fun goToMainMenuActivity(view: View){
        this.onBackPressed()
    }
}