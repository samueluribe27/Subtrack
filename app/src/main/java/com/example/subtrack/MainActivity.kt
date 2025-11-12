package com.example.subtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.subtrack.ui.home.HomeActivity

/**
 * Actividad principal de la aplicación Subtrack.
 * Maneja la pantalla de inicio de sesión de la aplicación.
 * 
 * @author [Tu Nombre]
 */
class MainActivity : AppCompatActivity() {
    /**
     * Método llamado cuando la actividad es creada.
     * Configura la vista y los listeners necesarios.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el layout de la actividad
        setContentView(R.layout.activity_login)
        
        // Configuración de la interfaz de usuario
        val inputPassword = findViewById<EditText>(R.id.etPassword)
        val btnEntrar = findViewById<Button>(R.id.btnLogin)

        // Configura el listener del botón de inicio de sesión
        btnEntrar.setOnClickListener {
            val password = inputPassword.text.toString()

            // Validación básica de contraseña
            if (password.length > 6) {
                // ✅ Contraseña válida → Navegar a la pantalla principal
                navigateToHome()
            } else {
                // ❌ Contraseña inválida → Mostrar mensaje de error
                showPasswordError()
            }
        }
    }
    
    /**
     * Navega a la pantalla principal de la aplicación.
     */
    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    
    /**
     * Muestra un mensaje de error cuando la contraseña es inválida.
     */
    private fun showPasswordError() {
        Toast.makeText(this, "La contraseña debe tener más de 6 dígitos", Toast.LENGTH_SHORT).show()
    }
