package com.example.subtrack.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.subtrack.SubTrackApplication
import com.example.subtrack.databinding.FragmentDashboardBinding
import com.example.subtrack.ui.ViewModelFactory
import java.text.NumberFormat
import java.util.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireActivity().application as SubTrackApplication
        val factory = ViewModelFactory(app.subscriptionRepository, app.categoryRepository)
        dashboardViewModel = ViewModelProvider(this, factory)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        observeData()

        return binding.root
    }

    private fun observeData() {
        dashboardViewModel.totalSpending.observe(viewLifecycleOwner) { total ->
            binding.textViewTotalSpending.text = formatCurrency(total)
        }

        dashboardViewModel.activeSubscriptions.observe(viewLifecycleOwner) { count ->
            binding.textViewActiveSubscriptions.text = count.toString()
        }

        dashboardViewModel.nextPayment.observe(viewLifecycleOwner) { nextPayment ->
            binding.textViewNextPayment.text = nextPayment
        }
    }

    private fun formatCurrency(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        return format.format(amount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
