package com.example.subtrack.ui.subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subtrack.databinding.FragmentSubscriptionsBinding

class SubscriptionsFragment : Fragment() {

    private var _binding: FragmentSubscriptionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var subscriptionsViewModel: SubscriptionsViewModel
    private lateinit var subscriptionsAdapter: SubscriptionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        subscriptionsViewModel = ViewModelProvider(this)[SubscriptionsViewModel::class.java]
        _binding = FragmentSubscriptionsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeData()

        return binding.root
    }

    private fun setupRecyclerView() {
        subscriptionsAdapter = SubscriptionsAdapter { subscription ->
            // Handle subscription click
        }
        
        binding.recyclerViewSubscriptions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = subscriptionsAdapter
        }
    }

    private fun observeData() {
        subscriptionsViewModel.subscriptions.observe(viewLifecycleOwner) { subscriptions ->
            subscriptionsAdapter.submitList(subscriptions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
