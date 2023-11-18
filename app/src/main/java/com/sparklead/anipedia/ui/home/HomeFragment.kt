package com.sparklead.anipedia.ui.home

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.HeroCarouselStrategy
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.sparklead.anipedia.R
import com.sparklead.anipedia.databinding.FragmentHomeBinding
import com.sparklead.anipedia.model.OfflineAnimeDb
import com.sparklead.anipedia.model.OfflineTopAnimeDb
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.model.all_anime.Images
import com.sparklead.anipedia.model.all_anime.Jpg
import com.sparklead.anipedia.ui.adapter.AnimeListAdapter
import com.sparklead.anipedia.ui.adapter.CarouselAdapter
import com.sparklead.anipedia.utils.CarouselItem
import com.sparklead.anipedia.utils.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: AnimeListAdapter
    private lateinit var multiBrowseCenteredCarouselLayoutManager: CarouselLayoutManager
    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        viewModel.getOfflineDb()
        viewModel.getOfflineTopAnime()


        if (Network.isOnline(requireContext())) {
            viewModel.getAllAnimeList()
            viewModel.getTopAnimeList()
        } else {
            Toast.makeText(requireContext(), "Offline - Showing limited content", Toast.LENGTH_LONG)
                .show()
        }

        viewModel.saveFirstLogin()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.homeUiState.collect {
                when (it) {
                    is HomeUiState.Error -> {
                        onError(it.message)
                    }

                    is HomeUiState.Loading -> {

                    }

                    is HomeUiState.AllAnimeListSuccess -> {
                        onAllAnimeListSuccess(it.animeList)
                    }

                    is HomeUiState.TopAnimeSuccess -> {
                        second(it.list)
                    }

                    is HomeUiState.AllAnimeDbListSuccess -> {
                        onAllDbAnimeSuccess(it.list)
                    }

                    is HomeUiState.TopAnimeDbSuccess -> {
                        onTopDbAnimeListSuccess(it.list)
                    }
                }
            }
        }
    }

    private fun onAllDbAnimeSuccess(list: List<OfflineAnimeDb>) {
        val listAnime = ArrayList<AnimeResponse>()
        list.forEach {
            listAnime.add(it.toAnimeResponse())
        }
        onAllAnimeListSuccess(listAnime)
    }

    private fun onTopDbAnimeListSuccess(list: List<OfflineTopAnimeDb>) {
        val listAnime = ArrayList<AnimeResponse>()
        list.forEach {
            listAnime.add(it.toAnimeResponse())
        }
        first(listAnime)
    }

    private fun onAllAnimeListSuccess(animeList: List<AnimeResponse>) {
        adapter = AnimeListAdapter(animeList)
        binding.rvAnimeList.adapter = adapter
        binding.rvAnimeList.layoutManager = GridLayoutManager(requireActivity(), 2)

        adapter.onItemClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToAnimeDetailFragment(it)
            findNavController().navigate(action)
        }
        saveAllAnimeListDb(animeList)
    }

    private fun saveAllAnimeListDb(animeList: List<AnimeResponse>) {
        val anime = ArrayList<OfflineAnimeDb>()
        animeList.forEach {
            anime.add(it.toOfflineAnime())
        }
        viewModel.saveOfflineAnime(anime)
    }

    private fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


    private fun saveTopAnimeDb(list: List<AnimeResponse>) {
        val anime = ArrayList<OfflineTopAnimeDb>()
        list.forEach {
            anime.add(it.toOfflineTopAnime())
        }
        viewModel.saveOfflineTopAnime(anime)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun OfflineAnimeDb.toAnimeResponse(): AnimeResponse {
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

    private fun AnimeResponse.toOfflineAnime(): OfflineAnimeDb {
        return OfflineAnimeDb(
            mal_id = this.mal_id,
            background = this.background,
            episodes = this.episodes,
            images = this.images?.jpg?.large_image_url.toString(),
            rank = this.rank,
            score = this.score,
            synopsis = this.synopsis,
            title = this.title
        )
    }

    private fun AnimeResponse.toOfflineTopAnime(): OfflineTopAnimeDb {
        return OfflineTopAnimeDb(
            mal_id = this.mal_id,
            background = this.background,
            episodes = this.episodes,
            images = this.images?.jpg?.large_image_url.toString(),
            rank = this.rank,
            score = this.score,
            synopsis = this.synopsis,
            title = this.title
        )
    }

    private fun OfflineTopAnimeDb.toAnimeResponse(): AnimeResponse {
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

    private fun first(list: List<AnimeResponse>) {
        multiBrowseCenteredCarouselLayoutManager =
            CarouselLayoutManager(HeroCarouselStrategy())
        binding.carouselRvTopAnime.layoutManager = multiBrowseCenteredCarouselLayoutManager
        binding.carouselRvTopAnime.isNestedScrollingEnabled = false


        carouselAdapter = CarouselAdapter(
            object : CarouselAdapter.CarouselItemListener {
                override fun onItemClicked(item: CarouselItem, position: Int) {
                    binding.carouselRvTopAnime.scrollToPosition(
                        position
                    )
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToAnimeDetailFragment(list[position])
                    findNavController().navigate(action)
                }
            }, R.layout.item_carousel_anime
        )

        val carouselItem: MutableList<CarouselItem> = mutableListOf()
        for (item in list) {
            item.images?.jpg?.large_image_url?.let {
                item.title?.let { it1 ->
                    CarouselItem(
                        it,
                        it1
                    )
                }
            }?.let { carouselItem.add(it) }
        }
        binding.carouselRvTopAnime.adapter = carouselAdapter
        carouselAdapter.submitList(carouselItem)
    }

    private fun second(list: List<AnimeResponse>) {
        val carouselItem: MutableList<CarouselItem> = mutableListOf()
        for (item in list) {
            item.images?.jpg?.large_image_url?.let {
                item.title?.let { it1 ->
                    CarouselItem(
                        it,
                        it1
                    )
                }
            }?.let { carouselItem.add(it) }
        }
        carouselAdapter.submitList(carouselItem)
        saveTopAnimeDb(list)
    }
}