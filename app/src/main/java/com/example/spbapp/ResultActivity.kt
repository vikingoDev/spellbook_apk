package com.example.spbapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultactivity) // Asegúrate de que el layout sea correcto

        // Obtén los resultados pasados desde la intención
        val results = intent.getParcelableArrayListExtra<Spell>("results")

        // Configura el RecyclerView con el adaptador de hechizos
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SpellAdapter(results ?: listOf())
    }
}
