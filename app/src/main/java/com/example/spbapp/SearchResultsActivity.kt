package com.example.spbapp
import android.widget.Toast
import android.widget.EditText
import com.google.android.gms.tasks.Tasks
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results)  // Asegúrate de que el layout sea correcto

        searchEditText = findViewById(R.id.search_edit_text)
        searchButton = findViewById(R.id.search_button)

        searchButton.setOnClickListener {
            val searchText = searchEditText.text.toString()
            performSearch(searchText)
        }
    }

    private fun performSearch(query: String) {
        val firestore = FirebaseFirestore.getInstance()
        val collections = listOf("a_sp", "b_sp","c_sp","d_sp","e_sp","f_sp","g_sp","h_sp","i_sp","j_sp") // Lista de nombres de colecciones

        val searchTasks = collections.map { collectionName ->
            firestore.collection(collectionName)
                .whereGreaterThanOrEqualTo("name", query)
                .whereLessThanOrEqualTo("name", query + '\uf8ff')
                .get()
                .addOnSuccessListener { documents ->
                    val results = documents.map { it.toObject(Spell::class.java) }
                    showResults(results)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting documents from $collectionName: ${exception.message}", Toast.LENGTH_LONG).show()
                }
        }

        // Wait for all search tasks to complete
        Tasks.whenAllSuccess<Any>(searchTasks)
            .addOnSuccessListener {
                // All tasks complete, if needed, handle combined results here
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error executing search tasks: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun showResults(spellList: List<Spell>) {
        val intent = Intent(this, ResultActivity::class.java) // Cambia a la actividad que maneje los resultados
        intent.putParcelableArrayListExtra("results", ArrayList(spellList))
        startActivity(intent)
    }
}
