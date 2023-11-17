package com.sparklead.anipedia.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sparklead.anipedia.databinding.FragmentFavoriteBinding
import com.sparklead.anipedia.model.AnimeDb
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.model.all_anime.Images
import com.sparklead.anipedia.model.all_anime.Jpg
import com.sparklead.anipedia.ui.adapter.AnimeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: AnimeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        viewModel.getAllAnimeList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.favoriteUiState.collect {
                when (it) {
                    is FavoriteUiState.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is FavoriteUiState.Loading -> {}
                    is FavoriteUiState.Success -> {
                        onSuccessList(it.list)
                    }
                }
            }
        }
    }

    private fun onSuccessList(list: List<AnimeDb>) {
        val listAnime = ArrayList<AnimeResponse>()
        list.forEach {
            listAnime.add(it.toAnimeResponse())
        }
        setUpRecycleView(listAnime)
    }

    private fun setUpRecycleView(list: List<AnimeResponse>) {
        adapter = AnimeListAdapter(list)
        binding.rvAnimeList.adapter = adapter
        binding.rvAnimeList.layoutManager = GridLayoutManager(requireActivity(), 2)

        adapter.onItemClick = {
            val action = FavoriteFragmentDirections.actionFavoritesFragmentToAnimeDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun AnimeDb.toAnimeResponse(): AnimeResponse {
        return AnimeResponse(
            mal_id = this.mal_id,
            background = this.background,
            episodes = this.episodes,
            images = Images(Jpg(null, large_image_url = this.images, null), null),
            rank = this.rank,
            score = this.score,
            synopsis = this.synopsis,
            title = this.title
        )
    }
}