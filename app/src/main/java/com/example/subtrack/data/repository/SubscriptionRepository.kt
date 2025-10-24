package com.example.subtrack.data.repository

import com.example.subtrack.data.dao.SubscriptionDao
import com.example.subtrack.data.model.Subscription
import kotlinx.coroutines.flow.Flow
class SubscriptionRepository(
    private val subscriptionDao: SubscriptionDao
) {
    fun getAllActiveSubscriptions(): Flow<List<Subscription>> {
        return subscriptionDao.getAllActiveSubscriptions()
    }

    fun getAllSubscriptions(): Flow<List<Subscription>> {
        return subscriptionDao.getAllSubscriptions()
    }

    suspend fun getSubscriptionById(id: Long): Subscription? {
        return subscriptionDao.getSubscriptionById(id)
    }

    fun getSubscriptionsByCategory(category: String): Flow<List<Subscription>> {
        return subscriptionDao.getSubscriptionsByCategory(category)
    }

    suspend fun getTotalMonthlySpending(): Double {
        return subscriptionDao.getTotalMonthlySpending()
    }

    suspend fun getActiveSubscriptionCount(): Int {
        return subscriptionDao.getActiveSubscriptionCount()
    }

    fun getSubscriptionsByBillingDay(day: Int): Flow<List<Subscription>> {
        return subscriptionDao.getSubscriptionsByBillingDay(day)
    }

    suspend fun insertSubscription(subscription: Subscription): Long {
        return subscriptionDao.insertSubscription(subscription)
    }

    suspend fun updateSubscription(subscription: Subscription) {
        subscriptionDao.updateSubscription(subscription)
    }

    suspend fun deleteSubscription(subscription: Subscription) {
        subscriptionDao.deleteSubscription(subscription)
    }

    suspend fun deactivateSubscription(id: Long) {
        subscriptionDao.deactivateSubscription(id)
    }
}
