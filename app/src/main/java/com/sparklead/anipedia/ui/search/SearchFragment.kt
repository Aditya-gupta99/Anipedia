package com.sparklead.anipedia.ui.search

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.sparklead.anipedia.databinding.FragmentSearchBinding
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.ui.adapter.AnimeListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: AnimeListAdapter
    private val handler = Handler()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.getSearchAnimeList("")
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.searchUiState.collect {
                when (it) {
                    is SearchUiState.Empty -> {}
                    is SearchUiState.Error -> {
                        onError(it.message)
                    }

                    is SearchUiState.Success -> {
                        onSearchSuccess(it.list)
                    }
                }
            }
        }

        binding.svAnime.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    animeFilter(text)
                }, 200)
                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }
        })
    }

    private fun onSearchSuccess(list: List<AnimeResponse>) {
        adapter = AnimeListAdapter(list)
        binding.rvAnimeList.adapter = adapter
        binding.rvAnimeList.layoutManager = GridLayoutManager(requireActivity(), 2)

        adapter.onItemClick = {
            val action = SearchFragmentDirections.actionSearchFragmentToAnimeDetailFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun animeFilter(text: String?) {
        text?.let { viewModel.getSearchAnimeList(it) }
    }
}