package com.example.subtrack.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.subtrack.data.dao.CategoryDao
import com.example.subtrack.data.dao.SubscriptionDao
import com.example.subtrack.data.model.Category
import com.example.subtrack.data.model.Subscription

@Database(
    entities = [Subscription::class, Category::class],
    version = 1,
    exportSchema = false
)
abstract class SubTrackDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: SubTrackDatabase? = null

        fun getDatabase(context: Context): SubTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubTrackDatabase::class.java,
                    "subtrack_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
