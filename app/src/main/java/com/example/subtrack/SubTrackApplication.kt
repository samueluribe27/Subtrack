package com.example.subtrack

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.subtrack.data.database.SubTrackDatabase
import com.example.subtrack.data.repository.CategoryRepository
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.workers.NotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Clase Application personalizada para la aplicación Subtrack.
 * Se encarga de la inicialización de componentes y configuraciones globales.
 * 
 * @author [Tu Nombre]
 */
class SubTrackApplication : Application() {
    
    // Instancia de la base de datos
    val database by lazy { SubTrackDatabase.getDatabase(this) }
    
    // Repositorios
    val subscriptionRepository by lazy { 
        SubscriptionRepository(
            database.subscriptionDao(),
            database.subscriptionWithPaymentsDao()
        ) 
    }
    
    val categoryRepository by lazy { 
        CategoryRepository(database.categoryDao()) 
    }
    
    // Ámbito de corrutinas para operaciones en segundo plano
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    
    override fun onCreate() {
        super.onCreate()
        
        // Inicializar canales de notificación
        createNotificationChannel()
        
        // Inicializar datos por defecto
        initializeDefaultData()
        
        // Programar trabajos en segundo plano
        scheduleRecurringWork()
    }
    
    /**
     * Crea el canal de notificaciones para Android 8.0 (API 26) y superiores.
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Recordatorios de Suscripciones",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones para recordatorios de pago de suscripciones"
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    /**
     * Inicializa datos por defecto en la base de datos.
     */
    private fun initializeDefaultData() {
        applicationScope.launch {
            categoryRepository.initializeDefaultCategories()
        }
    }
    
    /**
     * Programa trabajos recurrentes, como notificaciones de pago.
     */
    private fun scheduleRecurringWork() {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            1, TimeUnit.DAYS,  // Cada 24 horas
            1, TimeUnit.HOURS   // Flexibilidad de 1 hora
        ).build()
        
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "subscription_notification_work",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }
    
    companion object {
        const val NOTIFICATION_CHANNEL_ID = "subscription_reminder_channel"
    }
}
