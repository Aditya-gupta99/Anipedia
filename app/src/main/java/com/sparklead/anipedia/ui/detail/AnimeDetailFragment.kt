package com.sparklead.anipedia.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sparklead.anipedia.R
import com.sparklead.anipedia.databinding.FragmentAnimeDetailBinding
import com.sparklead.anipedia.model.AnimeDb
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.utils.GlideLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnimeDetailFragment : Fragment() {

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AnimeDetailFragmentArgs by navArgs()
    private lateinit var viewModel: AnimeDetailsViewModel
    private lateinit var animeDb: AnimeDb

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AnimeDetailsViewModel::class.java]
        setUpUi(args.AnimeDetails)
        animeDb = args.AnimeDetails.toAnimeDb()
        animeDb.title?.let { viewModel.getAnimeCount(it) }
        return binding.root
    }

    private fun setUpUi(details: AnimeResponse) {
        binding.tvAnimeTitle.text = details.title
        binding.tvRanking.text = details.rank.toString()
        binding.tvEpisodes.text = details.episodes.toString()
        details.images?.jpg?.large_image_url?.let {
            GlideLoader(requireContext()).loadAnimePicture(
                it, binding.ivAnime
            )
        }
        if (details.background != null) binding.tvAnimeBackground.text = details.background else {
            binding.tvAnimeBackgroundTitle.visibility = View.GONE
            binding.tvAnimeBackground.visibility = View.GONE
        }
        if (details.synopsis != null) binding.tvAnimeSynopsis.text = details.synopsis else {
            binding.tvAnimeSynopsisTitle.visibility = View.GONE
            binding.tvAnimeSynopsis.visibility = View.GONE
        }
        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.animeDetailUiState.collect {
                when (it) {
                    is AnimeDetailUiState.Empty -> {}
                    is AnimeDetailUiState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is AnimeDetailUiState.SuccessCount -> {
                        onSuccessCount(it.count)
                    }
                }
            }
        }

        binding.btnSave.setOnClickListener {
            binding.btnSave.visibility = View.GONE
            binding.btnUnsave.visibility = View.VISIBLE
            saveToFavorite()
        }
        binding.btnUnsave.setOnClickListener {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnUnsave.visibility = View.GONE
            unSaveFavorite()
        }
        binding.btnBack.setOnClickListener {

        }
    }

    private fun onSuccessCount(count: Int) {
        Log.e("@@@", count.toString())
        if (count > 0) {
            binding.btnSave.visibility = View.GONE
            binding.btnUnsave.visibility = View.VISIBLE
        } else {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnUnsave.visibility = View.GONE
        }
    }

    private fun unSaveFavorite() {
        viewModel.unSaveAnimeInDB(animeDb)
    }

    private fun saveToFavorite() {
        viewModel.saveAnimeInDB(animeDb)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun AnimeResponse.toAnimeDb(): AnimeDb {
        return AnimeDb(
            mal_id = this.mal_id,
            background = this.background,
            episodes = this.episodes,
            favorites = this.favorites,
            images = this.images?.jpg?.large_image_url.toString(),
            rank = this.rank,
            score = this.score,
            synopsis = this.synopsis,
            title = this.title
        )
    }
}