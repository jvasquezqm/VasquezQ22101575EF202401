package com.example.vasquezq22101575ef202401

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class RegisterEquiposActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_equipos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etNameEquipo: EditText = findViewById(R.id.etNameEquipo)
        val etEscudoEquipo: EditText = findViewById(R.id.etEscudoEquipo)
        val btGuardar: Button = findViewById(R.id.btGuardar)
        val btRegistrarEntre: Button = findViewById(R.id.btRegistrarEntren)
        val btListarEntrenamientos: Button = findViewById(R.id.btListarEntrena)
        val db = FirebaseFirestore.getInstance()
        btGuardar.setOnClickListener {
            val nameEquipo = etNameEquipo.text.toString()
            val escudoEquipo = etEscudoEquipo.text.toString()
            if (nameEquipo.isEmpty() || escudoEquipo.isEmpty()) {
                Toast.makeText(this, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                //guardar en la base de datos firestore
                val equipo = hashMapOf(
                    "name" to nameEquipo,
                    "escudo" to escudoEquipo
                )
                db.collection("equipos")
                    .add(equipo)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Equipo registrado", Toast.LENGTH_SHORT).show()
                        etNameEquipo.setText("")
                        etEscudoEquipo.setText("")
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al registrar el equipo", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        btRegistrarEntre.setOnClickListener {
            val intent = Intent(this, RegisterEntrenamientosActivity::class.java)
            startActivity(intent)
        }
        btListarEntrenamientos.setOnClickListener {
            val intent = Intent(this, ListarEntrenamientosActivity::class.java)
            startActivity(intent)
        }



    }
}