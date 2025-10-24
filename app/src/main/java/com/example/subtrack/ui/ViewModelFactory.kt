package com.example.subtrack.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.subtrack.data.repository.CategoryRepository
import com.example.subtrack.data.repository.SubscriptionRepository
import com.example.subtrack.ui.analysis.AnalysisViewModel
import com.example.subtrack.ui.create.CreateSubscriptionViewModel
import com.example.subtrack.ui.dashboard.DashboardViewModel
import com.example.subtrack.ui.home.HomeViewModel
import com.example.subtrack.ui.subscriptions.SubscriptionsViewModel

class ViewModelFactory(
    private val subscriptionRepository: SubscriptionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            when {
                modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                    HomeViewModel() as T
                }
                modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                    DashboardViewModel(subscriptionRepository) as T
                }
                modelClass.isAssignableFrom(SubscriptionsViewModel::class.java) -> {
                    SubscriptionsViewModel(subscriptionRepository) as T
                }
                modelClass.isAssignableFrom(CreateSubscriptionViewModel::class.java) -> {
                    CreateSubscriptionViewModel(subscriptionRepository) as T
                }
                modelClass.isAssignableFrom(AnalysisViewModel::class.java) -> {
                    AnalysisViewModel(subscriptionRepository) as T
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
