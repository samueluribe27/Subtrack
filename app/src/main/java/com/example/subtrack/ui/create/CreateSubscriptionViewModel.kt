package com.example.subtrack.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.model.Subscription
import com.example.subtrack.data.repository.SubscriptionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateSubscriptionViewModel @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> = _isSaved

    fun saveSubscription(name: String, price: Double, category: String, billingDate: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val subscription = Subscription(
                    name = name,
                    price = price,
                    category = category,
                    billingDate = billingDate
                )
                subscriptionRepository.insertSubscription(subscription)
                _isSaved.value = true
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
