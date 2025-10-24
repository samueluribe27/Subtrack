package com.example.subtrack.ui.analysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.subtrack.SubTrackApplication
import com.example.subtrack.databinding.FragmentAnalysisBinding
import com.example.subtrack.ui.ViewModelFactory

class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    private lateinit var analysisViewModel: AnalysisViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val app = requireActivity().application as SubTrackApplication
        val factory = ViewModelFactory(app.subscriptionRepository, app.categoryRepository)
        analysisViewModel = ViewModelProvider(this, factory)[AnalysisViewModel::class.java]
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)

        setupCharts()
        observeData()

        return binding.root
    }

    private fun setupCharts() {
        // Setup pie chart for categories
        binding.pieChartCategories.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
        }

        // Setup bar chart for monthly spending
        binding.barChartMonthly.apply {
            description.isEnabled = false
            setExtraOffsets(10f, 10f, 10f, 10f)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
        }
    }

    private fun observeData() {
        analysisViewModel.categoryData.observe(viewLifecycleOwner) { data ->
            // Update pie chart with category data
        }

        analysisViewModel.monthlyData.observe(viewLifecycleOwner) { data ->
            // Update bar chart with monthly data
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}