package com.sparklead.anipedia.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sparklead.anipedia.R
import com.sparklead.anipedia.databinding.FragmentAnimeDetailBinding
import com.sparklead.anipedia.model.all_anime.AnimeResponse
import com.sparklead.anipedia.utils.GlideLoader

class AnimeDetailFragment : Fragment() {

    private var _binding: FragmentAnimeDetailBinding? = null
    private val binding get() = _binding!!
    private val args: AnimeDetailFragmentArgs by navArgs()
    private var save = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailBinding.inflate(inflater, container, false)
        setUpUi(args.AnimeDetails)
        return binding.root
    }

    private fun setUpUi(details: AnimeResponse) {
        binding.tvAnimeTitle.text = details.title
        binding.tvRanking.text = details.rank.toString()
        binding.tvEpisodes.text = details.episodes.toString()
        details.images?.jpg?.large_image_url?.let {
            GlideLoader(requireContext()).loadAnimePicture(
                it,
                binding.ivAnime
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
        if(save) {
            binding.btnSave.visibility = View.GONE
            binding.btnUnsave.visibility = View.VISIBLE
        } else {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnUnsave.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            save = true
            binding.btnSave.visibility = View.GONE
            binding.btnUnsave.visibility = View.VISIBLE
        }
        binding.btnUnsave.setOnClickListener {
            save = false
            binding.btnSave.visibility = View.VISIBLE
            binding.btnUnsave.visibility = View.GONE
        }
        binding.btnBack.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}