package com.example.vasquezq22101575ef202401

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class RegisterEntrenamientosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_entrenamientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spEquipoLocal: Spinner = findViewById(R.id.spEquipoLocal)
        val spEquipoVisitante: Spinner = findViewById(R.id.spEquipoVisita)
        val etLocal: EditText = findViewById(R.id.etLocal)
        val etVisitante: EditText = findViewById(R.id.etVisita)
        val etEmpate: EditText = findViewById(R.id.etEmpate)
        val btnSave: Button = findViewById(R.id.btRegistrarEntren)  // Assuming you have a save button
        val db = FirebaseFirestore.getInstance()

        db.collection("equipos").get()
            .addOnSuccessListener { result ->
                val equiposList = mutableListOf<String>()
                for (document in result) {
                    equiposList.add(document.getString("name") ?: "")
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, equiposList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spEquipoLocal.adapter = adapter
                spEquipoVisitante.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error traer equipos: ${exception.message}", Toast.LENGTH_LONG).show()
            }

        spEquipoLocal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                etLocal.setText(parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                etLocal.setText("")
            }
        }

        spEquipoVisitante.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                etVisitante.setText(parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                etVisitante.setText("")
            }
        }

        btnSave.setOnClickListener {
            val entrenamiento = hashMapOf(
                "local" to etLocal.text.toString(),
                "visitante" to etVisitante.text.toString(),
                "empate" to etEmpate.text.toString(),
                "equipoLocal" to spEquipoLocal.selectedItem.toString(),
                "equipoVisitante" to spEquipoVisitante.selectedItem.toString()
            )

            db.collection("entrenamientos")
                .add(entrenamiento)
                .addOnSuccessListener {
                    Toast.makeText(this, "Entrenamiento registrado", Toast.LENGTH_SHORT).show()
                    etLocal.setText("")
                    etVisitante.setText("")
                    etEmpate.setText("")
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al registrar el entrenamiento", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
