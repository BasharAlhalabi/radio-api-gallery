package com.example.galleryapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.galleryapp.R
import com.example.galleryapp.ui.adapter.StationAdapter
import com.example.galleryapp.viewmodel.RadioListViewModel
import com.example.galleryapp.core.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {
    private val viewModel: RadioListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.stations.collectLatest { uiState ->
            val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
            when (uiState) {
                is UiState.Loading -> progressBar.visibility = View.VISIBLE
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    if(uiState.data.isEmpty()) {
                        notifyError()
                        return@collectLatest
                    }

                    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
                    recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                    recyclerView.adapter = StationAdapter(uiState.data) { radioStation ->
                        val action = ListFragmentDirections
                            .actionListFragmentToDetailsFragment(radioStation.id,
                                radioStation.name,
                                radioStation.logo100x100)
                        findNavController().navigate(action)
                    }
                }
                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    notifyError()
                }
            }
        }
    }

    private fun notifyError() {
        Toast.makeText(context, getString(R.string.error_loading_stations), Toast.LENGTH_LONG).show()
    }
}
