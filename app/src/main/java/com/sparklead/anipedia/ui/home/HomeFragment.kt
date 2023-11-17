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
import com.google.android.material.carousel.MultiBrowseCarouselStrategy
import com.sparklead.anipedia.R
import com.sparklead.anipedia.databinding.FragmentHomeBinding
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.ui.adapter.AnimeListAdapter
import com.sparklead.anipedia.ui.adapter.CarouselAdapter
import com.sparklead.anipedia.utils.CarouselItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: AnimeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.VISIBLE

        viewModel.getAllAnimeList()
        viewModel.getTopAnimeList()

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
                        onTopAnimeListSuccess(it.list)
                    }
                }
            }
        }
    }

    private fun onAllAnimeListSuccess(animeList: List<AnimeResponse>) {
        adapter = AnimeListAdapter(animeList)
        binding.rvAnimeList.adapter = adapter
        binding.rvAnimeList.layoutManager = GridLayoutManager(requireActivity(), 2)

        adapter.onItemClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToAnimeDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun onTopAnimeListSuccess(list: List<AnimeResponse>) {
        val multiBrowseCenteredCarouselLayoutManager =
            CarouselLayoutManager(MultiBrowseCarouselStrategy())
        binding.carouselRvTopAnime.layoutManager = multiBrowseCenteredCarouselLayoutManager
        binding.carouselRvTopAnime.isNestedScrollingEnabled = false


        val adapter = CarouselAdapter(
            object : CarouselAdapter.CarouselItemListener {
                override fun onItemClicked(item: CarouselItem, position: Int) {
                    binding.carouselRvTopAnime.scrollToPosition(
                        position
                    )
                    val action = HomeFragmentDirections.actionHomeFragmentToAnimeDetailFragment(list[position])
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
        binding.carouselRvTopAnime.adapter = adapter
        adapter.submitList(carouselItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}