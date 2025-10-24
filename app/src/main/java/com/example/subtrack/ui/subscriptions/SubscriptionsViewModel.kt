package com.example.subtrack.ui.subscriptions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.model.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import kotlinx.coroutines.launch

class SubscriptionsViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _subscriptions = MutableLiveData<List<Subscription>>()
    val subscriptions: LiveData<List<Subscription>> = _subscriptions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadSubscriptions()
    }

    private fun loadSubscriptions() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                subscriptionRepository.getAllActiveSubscriptions().collect { subscriptions ->
                    _subscriptions.value = subscriptions
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
                _subscriptions.value = emptyList()
            }
        }
    }

    fun deleteSubscription(subscription: Subscription) {
        viewModelScope.launch {
            subscriptionRepository.deleteSubscription(subscription)
        }
    }

    fun deactivateSubscription(id: Long) {
        viewModelScope.launch {
            subscriptionRepository.deactivateSubscription(id)
        }
    }
}
