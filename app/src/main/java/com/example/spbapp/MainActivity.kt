package com.example.spbapp
import android.widget.Toast
import android.content.Intent
import com.google.android.gms.tasks.Tasks
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity {
    // MainActivity.kt
    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.main_activity)

            val searchButton: Button = findViewById(R.id.search_button)
            val searchInput: EditText = findViewById(R.id.search_input)

            searchButton.setOnClickListener {
                val searchText = searchEditText.text.toString()
                if (searchText.isNotEmpty()) {
                    val intent = Intent(this, SearchResultsActivity::class.java)
                    intent.putExtra("searchText", searchText)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun performSearch() {
        val queryText = searchEditText.text.toString().trim()
        if (queryText.isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            return
        }

        val firestore = FirebaseFirestore.getInstance()
        val collections = listOf("spells", "otherCollection1", "otherCollection2") // Añade tus colecciones aquí

        val tasks = collections.map { collection ->
            firestore.collection(collection)
                .whereGreaterThanOrEqualTo("name", queryText)
                .whereLessThanOrEqualTo("name", queryText + '\uf8ff')
                .get()
                .continueWith { task ->
                    if (task.isSuccessful) {
                        task.result?.documents?.map { it.toObject(Spell::class.java) } ?: emptyList()
                    } else {
                        emptyList<Spell>()
                    }
                }
        }

        Tasks.whenAllSuccess<List<Spell>>(tasks).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val results = task.result.flatMap { it }
                if (results.isNotEmpty()) {
                    val intent = Intent(this, SearchResultsActivity::class.java)
                    intent.putParcelableArrayListExtra("results", ArrayList(results))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error performing search", Toast.LENGTH_SHORT).show()
            }
        }
    }

}