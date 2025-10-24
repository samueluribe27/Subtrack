package com.example.subtrack.ui.subscriptions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.subtrack.data.model.Subscription
import com.example.subtrack.databinding.ItemSubscriptionBinding
import java.text.NumberFormat
import java.util.*

class SubscriptionsAdapter(
    private val onSubscriptionClick: (Subscription) -> Unit
) : ListAdapter<Subscription, SubscriptionsAdapter.SubscriptionViewHolder>(SubscriptionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val binding = ItemSubscriptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SubscriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SubscriptionViewHolder(
        private val binding: ItemSubscriptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subscription: Subscription) {
            binding.apply {
                textViewName.text = subscription.name
                textViewPrice.text = formatCurrency(subscription.price)
                textViewCategory.text = subscription.category
                textViewBillingDate.text = "Día ${subscription.billingDate}"

                root.setOnClickListener {
                    onSubscriptionClick(subscription)
                }
            }
        }

        private fun formatCurrency(amount: Double): String {
            val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
            return format.format(amount)
        }
    }

    class SubscriptionDiffCallback : DiffUtil.ItemCallback<Subscription>() {
        override fun areItemsTheSame(oldItem: Subscription, newItem: Subscription): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Subscription, newItem: Subscription): Boolean {
            return oldItem == newItem
        }
    }
}
