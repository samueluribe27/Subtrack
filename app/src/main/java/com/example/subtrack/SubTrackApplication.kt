package com.example.subtrack

import android.app.Application
import com.example.subtrack.data.database.SubTrackDatabase
import com.example.subtrack.data.repository.CategoryRepository
import com.example.subtrack.data.repository.SubscriptionRepository

class SubTrackApplication : Application() {
    
    val database by lazy { SubTrackDatabase.getDatabase(this) }
    val subscriptionRepository by lazy { SubscriptionRepository(database.subscriptionDao()) }
    val categoryRepository by lazy { CategoryRepository(database.categoryDao()) }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize default categories
        initializeDefaultData()
    }
    
    private fun initializeDefaultData() {
        // This will be called in a coroutine scope
    }
}
