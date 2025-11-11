package com.example.subtrack.ui.subscriptions

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.subtrack.R
import com.example.subtrack.ui.analysis.AnalysisActivity
import com.example.subtrack.ui.home.HomeActivity
import com.example.subtrack.ui.view.Subscription // Importación de la clase de datos global
import com.example.subtrack.ui.view.ViewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class CreateSubscriptionActivity : AppCompatActivity() {

    // Vistas - Se usan los IDs del layout actualizado
    private lateinit var etName: EditText
    private lateinit var spinnerCategory: Spinner // Spinner añadido
    private lateinit var etPrice: EditText
    private lateinit var etNextPaymentDate: EditText
    private lateinit var btnCreate: Button
    private lateinit var btnCancel: Button

    // Formateador de fecha
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subs)

        // Inicialización de vistas (Usando los IDs del layout)
        etName = findViewById(R.id.etName)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        etPrice = findViewById(R.id.etPrice)
        etNextPaymentDate = findViewById(R.id.etNextPaymentDate)
        btnCreate = findViewById(R.id.btnCreate)
        btnCancel = findViewById(R.id.btnCancel)
        val navView = findViewById<BottomNavigationView>(R.id.nav_subs)
        navView.selectedItemId = R.id.navigation_create

        setupCategorySpinner()
        setupDatePicker()
        setupCreateButton()
        setupBottomNavigation(navView)
        setupCancelButton()
    }

    private fun setupCategorySpinner() {
        // Categorías predefinidas
        val categories = listOf("Entretenimiento", "Música", "Trabajo", "Educación", "Otros")

        // Adaptador simple para el Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()

        // Configurar el click en el campo de fecha para mostrar el DatePicker
        etNextPaymentDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.time
                    // Muestra la fecha seleccionada en el EditText
                    etNextPaymentDate.setText(dateFormatter.format(selectedDate!!))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            // Asegura que la fecha mínima sea hoy o una fecha futura si es necesario
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000 // Para permitir seleccionar la fecha actual
            datePicker.show()
        }
    }

    private fun setupCreateButton() {
        btnCreate.setOnClickListener {
            if (validateInputs()) {
                val newSubscription = createSubscriptionObject()

                // 1. Crear el Intent para devolver el resultado
                val resultIntent = Intent()
                resultIntent.putExtra("NEW_SUBSCRIPTION", newSubscription)

                // 2. Establecer el resultado y finalizar
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Cierra esta Activity
            }
        }
    }

    private fun setupCancelButton() {
        btnCancel.setOnClickListener {
            // Cierra la Activity sin devolver resultados (Activity.RESULT_CANCELED)
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        if (etName.text.isNullOrBlank()) {
            Toast.makeText(this, "El nombre del servicio es obligatorio.", Toast.LENGTH_SHORT).show()
            return false
        }
        val price = etPrice.text.toString().toDoubleOrNull()
        if (price == null || price <= 0) {
            Toast.makeText(this, "Ingresa un precio válido y mayor que cero.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (selectedDate == null) {
            Toast.makeText(this, "Selecciona la próxima fecha de cobro.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createSubscriptionObject(): Subscription {
        val name = etName.text.toString().trim()
        val price = etPrice.text.toString().toDouble()
        val category = spinnerCategory.selectedItem.toString()
        val nextPaymentDateString = dateFormatter.format(selectedDate!!)

        return Subscription(
            id = UUID.randomUUID().toString(),
            name = name,
            price = price,
            category = category,
            nextPaymentDate = nextPaymentDateString
        )
    }

    private fun setupBottomNavigation(navView: BottomNavigationView) {
        // Lógica de navegación existente para moverte entre actividades
        navView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_view -> {
                    startActivity(Intent(this, ViewActivity::class.java))
                    true
                }
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.navigation_analysis -> {
                    startActivity(Intent(this, AnalysisActivity::class.java))
                    true
                }
                R.id.navigation_create -> true // Ya estamos aquí
                else -> false
            }
            // Puedes añadir finish() a las transiciones si no quieres que se apilen
        }
    }
}