package com.example.subtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.subtrack.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aquí se indica el layout que se mostrará al abrir la app
        setContentView(R.layout.activity_login)
        val inputPassword = findViewById<EditText>(R.id.etPassword)
        val btnEntrar = findViewById<Button>(R.id.btnLogin)

        btnEntrar.setOnClickListener {
            val password = inputPassword.text.toString()

            if (password.length > 6) {
                // ✅ Contraseña válida → Ir al layout Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // ❌ Contraseña demasiado corta → Mostrar mensaje
                Toast.makeText(this, "La contraseña debe tener más de 6 dígitos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }
