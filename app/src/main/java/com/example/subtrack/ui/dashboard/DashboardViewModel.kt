package com.example.subtrack.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.model.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _totalSpending = MutableLiveData<Double>()
    val totalSpending: LiveData<Double> = _totalSpending

    private val _activeSubscriptions = MutableLiveData<Int>()
    val activeSubscriptions: LiveData<Int> = _activeSubscriptions

    private val _nextPayment = MutableLiveData<String>()
    val nextPayment: LiveData<String> = _nextPayment

    private val _subscriptions = MutableLiveData<List<Subscription>>()
    val subscriptions: LiveData<List<Subscription>> = _subscriptions

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                subscriptionRepository.getAllActiveSubscriptions().collect { subscriptions ->
                    _subscriptions.value = subscriptions
                    
                    val total = subscriptions.sumOf { it.price }
                    _totalSpending.value = total
                    
                    _activeSubscriptions.value = subscriptions.size
                    
                    // Calculate next payment
                    val today = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
                    val nextBillingDay = subscriptions
                        .map { it.billingDate }
                        .filter { it >= today }
                        .minOrNull() ?: subscriptions.minOfOrNull { it.billingDate } ?: 1
                    
                    val daysUntilNext = if (nextBillingDay >= today) {
                        nextBillingDay - today
                    } else {
                        (30 - today) + nextBillingDay
                    }
                    
                    _nextPayment.value = "${daysUntilNext}d"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Set default values to prevent crashes
                _totalSpending.value = 0.0
                _activeSubscriptions.value = 0
                _nextPayment.value = "0d"
                _subscriptions.value = emptyList()
            }
        }
    }
}