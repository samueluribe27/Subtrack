package com.example.subtrack.data.dao

import androidx.room.*
import com.example.subtrack.data.model.Subscription
import com.example.subtrack.data.model.SubscriptionWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveSubscriptions(): Flow<List<Subscription>>

    @Query("SELECT * FROM subscriptions ORDER BY name ASC")
    fun getAllSubscriptions(): Flow<List<Subscription>>

    @Query("SELECT * FROM subscriptions WHERE id = :id")
    suspend fun getSubscriptionById(id: Long): Subscription?

    @Query("SELECT * FROM subscriptions WHERE category = :category AND isActive = 1")
    fun getSubscriptionsByCategory(category: String): Flow<List<Subscription>>

    @Query("SELECT SUM(price) FROM subscriptions WHERE isActive = 1")
    suspend fun getTotalMonthlySpending(): Double

    @Query("SELECT COUNT(*) FROM subscriptions WHERE isActive = 1")
    suspend fun getActiveSubscriptionCount(): Int

    @Query("SELECT * FROM subscriptions WHERE isActive = 1 AND billingDate = :day")
    fun getSubscriptionsByBillingDay(day: Int): Flow<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: Subscription): Long

    @Update
    suspend fun updateSubscription(subscription: Subscription)

    @Delete
    suspend fun deleteSubscription(subscription: Subscription)

    @Query("UPDATE subscriptions SET isActive = 0 WHERE id = :id")
    suspend fun deactivateSubscription(id: Long)
}
