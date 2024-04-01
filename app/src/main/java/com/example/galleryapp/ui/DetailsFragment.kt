package com.example.galleryapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.core.UiState
import com.example.galleryapp.viewmodel.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel { parametersOf(args.stationId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectStationDetails(view)
    }

    private fun collectStationDetails(view: View) = viewLifecycleOwner.lifecycleScope.launch {
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        val buttonPlay = view.findViewById<ImageButton>(R.id.buttonPlay)

        viewModel.stationDetails.collectLatest { uiState ->
            when (uiState) {
                is UiState.Loading -> progressBar.visibility = View.VISIBLE
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    uiState.data?.let { stationDetails ->
                        textViewName.text = stationDetails.name
                        textViewDescription.text = stationDetails.description
                        buttonPlay.setOnClickListener {
                            Toast.makeText(context, "Should play ${stationDetails.name}", Toast.LENGTH_SHORT).show()
                            // TODO Here you would trigger the logic to play the stream, potentially using ExoPlayer
                        }
                        loadImage(view, stationDetails.logo300x300)
                    } ?: handleError()
                }
                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    handleError()
                }
            }
        }
    }

    private fun loadImage(view: View, imageUrl: String) {
        Glide.with(view)
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(view.findViewById(R.id.imageViewLogo))
    }

    /**
     * Tries to display station name and an image if available,
     * despite missing data (based on data from station list via args),
     * then notifies the user of an error.
     */
    private fun handleError() {
        view?.apply {
            findViewById<TextView>(R.id.textViewName).text = args.stationName
            loadImage(this, args.stationImageUrl)
        }
        notifyError()
    }

    private fun notifyError() {
        Toast.makeText(context, getString(R.string.error_loading_station_details), Toast.LENGTH_SHORT).show()
    }
}
