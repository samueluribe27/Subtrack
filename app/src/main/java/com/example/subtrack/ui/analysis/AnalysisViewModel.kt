package com.example.subtrack.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.subtrack.data.repository.SubscriptionRepository
import kotlinx.coroutines.launch

class AnalysisViewModel(
    private val subscriptionRepository: SubscriptionRepository
) : ViewModel() {

    private val _categoryData = MutableLiveData<Map<String, Double>>()
    val categoryData: LiveData<Map<String, Double>> = _categoryData

    private val _monthlyData = MutableLiveData<Map<String, Double>>()
    val monthlyData: LiveData<Map<String, Double>> = _monthlyData

    private val _totalSpending = MutableLiveData<Double>()
    val totalSpending: LiveData<Double> = _totalSpending

    init {
        loadAnalysisData()
    }

    private fun loadAnalysisData() {
        viewModelScope.launch {
            // Load subscriptions and calculate category spending
            subscriptionRepository.getAllActiveSubscriptions().collect { subscriptions ->
                val categorySpending = subscriptions.groupBy { it.category }
                    .mapValues { (_, subs) -> subs.sumOf { it.price } }
                _categoryData.value = categorySpending

                val total = subscriptions.sumOf { it.price }
                _totalSpending.value = total

                // For monthly data, you could implement historical data
                // For now, we'll use current month data
                _monthlyData.value = mapOf("Actual" to total)
            }
        }
    }
}
