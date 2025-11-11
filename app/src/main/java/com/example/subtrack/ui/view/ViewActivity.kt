package com.example.subtrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.subtrack.R
import com.example.subtrack.ui.analysis.AnalysisActivity
import com.example.subtrack.ui.home.HomeActivity
import com.example.subtrack.ui.subscriptions.CreateSubscriptionActivity
import com.example.subtrack.ui.subscriptions.SubscriptionAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.DecimalFormat

/**
 * Clase de datos que representa una Suscripción.
 * Es necesaria para el manejo de la lista en la Activity.
 */


class ViewActivity : AppCompatActivity() {

    // Referencias a las vistas para la lista y los totales
    private lateinit var subscriptionsRecyclerView: RecyclerView
    private lateinit var tvTotalMensual: TextView // TextView para gasto mensual
    private lateinit var tvTotalAnualEstimado: TextView // TextView para gasto anual
    private lateinit var subscriptionsAdapter: SubscriptionAdapter

    // Lista mutable con datos de prueba
    private var activeSubscriptions = mutableListOf(
        Subscription("1", "Netflix", 8.00, "entretenimiento","27/02/206"),
        Subscription("2", "Spotify", 10.99, "trabajo","28/02/2027"),
        Subscription("3", "Apple", 12.03, "otros","29/02/2026"),
        Subscription("4", "HBO Max", 10.00, "entretenimiento","31/04/2026")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        // 1. Inicialización de Vistas para Totales y Lista
        // Asegúrate de que estas IDs existan en R.layout.activity_view
        subscriptionsRecyclerView = findViewById(R.id.subscriptions_list)
        tvTotalMensual = findViewById(R.id.tvTotalMensual)
        tvTotalAnualEstimado = findViewById(R.id.tvTotalAnualEstimado)

        setupBottomNavigation()
        setupSubscriptionList()
        updateTotalsUI() // Inicializa los totales con la lista cargada
    }

    /**
     * Configura el RecyclerView y el adaptador, e implementa el callback de eliminación.
     */
    private fun setupSubscriptionList() {
        // Inicializa el adaptador con la función que se llamará al eliminar
        subscriptionsAdapter = SubscriptionAdapter(activeSubscriptions) { subscriptionId ->
            deleteSubscriptionById(subscriptionId)
        }

        subscriptionsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ViewActivity)
            adapter = subscriptionsAdapter
        }
    }

    /**
     * Elimina una suscripción por su ID, actualiza la lista y recalcula los totales.
     */
    private fun deleteSubscriptionById(id: String) {
        // Busca la posición del elemento para notificar correctamente al adaptador
        val position = activeSubscriptions.indexOfFirst { it.id == id }

        if (position != -1) {
            val subscriptionToRemove = activeSubscriptions[position]

            // 1. Eliminar del origen de datos (la lista mutable)
            activeSubscriptions.removeAt(position)

            // 2. Notificar al adaptador para actualizar la vista
            subscriptionsAdapter.notifyDataRemoved(position)

            // 3. Recalcular y actualizar la UI (los totales)
            updateTotalsUI()

            Log.d("ViewActivity", "Suscripción ${subscriptionToRemove.name} eliminada. Totales actualizados.")
        }
    }

    /**
     * Calcula los nuevos totales y actualiza los TextViews de gasto mensual y anual.
     */
    private fun updateTotalsUI() {
        val newTotalMonthlyCost = activeSubscriptions.sumOf { it.price }
        val newTotalYearlyCost = newTotalMonthlyCost * 12

        // Formato para mostrar el precio en moneda
        val currencyFormatter = DecimalFormat("$#,##0.00")

        // Actualizar la UI con los nuevos valores
        tvTotalMensual.text = currencyFormatter.format(newTotalMonthlyCost)
        tvTotalAnualEstimado.text = currencyFormatter.format(newTotalYearlyCost)
    }

    /**
     * Configuración de la navegación inferior para cambiar de Activity.
     */
    private fun setupBottomNavigation() {
        val viewPage = findViewById<BottomNavigationView>(R.id.nav_view)
        viewPage.selectedItemId = R.id.navigation_view

        viewPage.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_create -> {
                    val intent = Intent(this, CreateSubscriptionActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_analysis -> {
                    val intent = Intent(this, AnalysisActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.navigation_view -> true // Ya estamos en esta vista, no hacer nada
                else -> false
            }
        }
    }
}