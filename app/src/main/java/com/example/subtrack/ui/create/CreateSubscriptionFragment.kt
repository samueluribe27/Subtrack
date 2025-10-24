package com.example.subtrack.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.subtrack.SubTrackApplication
import com.example.subtrack.databinding.FragmentCreateSubscriptionBinding
import com.example.subtrack.ui.ViewModelFactory

class CreateSubscriptionFragment : Fragment() {

    private var _binding: FragmentCreateSubscriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var createSubscriptionViewModel: CreateSubscriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateSubscriptionBinding.inflate(inflater, container, false)
        
        try {
            val app = requireActivity().application as SubTrackApplication
            val factory = ViewModelFactory(app.subscriptionRepository, app.categoryRepository)
            createSubscriptionViewModel = ViewModelProvider(this, factory)[CreateSubscriptionViewModel::class.java]
            
            setupUI()
            observeData()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle error gracefully
        }

        return binding.root
    }

    private fun setupUI() {
        // Setup category spinner
        val categories = listOf("Entretenimiento", "Productividad", "Almacenamiento", "Noticias", "Otros")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapter

        // Setup billing date spinner (1-31)
        val billingDates = (1..31).map { it.toString() }
        val dateAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, billingDates)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerBillingDate.adapter = dateAdapter

        // Setup save button
        binding.buttonSave.setOnClickListener {
            saveSubscription()
        }

        // Setup cancel button
        binding.buttonCancel.setOnClickListener {
            clearForm()
        }
    }

    private fun observeData() {
        createSubscriptionViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.buttonSave.isEnabled = !isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        createSubscriptionViewModel.isSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                clearForm()
                // Show success message
            }
        }
    }

    private fun saveSubscription() {
        val name = binding.editTextName.text.toString().trim()
        val priceText = binding.editTextPrice.text.toString().trim()
        val category = binding.spinnerCategory.selectedItem.toString()
        val billingDate = binding.spinnerBillingDate.selectedItem.toString().toInt()

        if (name.isEmpty() || priceText.isEmpty()) {
            // Show error message
            return
        }

        val price = priceText.toDoubleOrNull() ?: 0.0
        createSubscriptionViewModel.saveSubscription(name, price, category, billingDate)
    }

    private fun clearForm() {
        binding.editTextName.text?.clear()
        binding.editTextPrice.text?.clear()
        binding.spinnerCategory.setSelection(0)
        binding.spinnerBillingDate.setSelection(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
