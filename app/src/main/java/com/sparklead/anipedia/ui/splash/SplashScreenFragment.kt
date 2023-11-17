package com.sparklead.anipedia.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sparklead.anipedia.R
import com.sparklead.anipedia.databinding.FragmentSplashScreenBinding
import com.sparklead.anipedia.utils.Constants
import com.sparklead.anipedia.utils.PrefManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.INVISIBLE

        var status = false
        lifecycleScope.launch {
            prefManager.readBooleanValue(Constants.FIRST_USER).collect {
                status = it
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (status) findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            else findNavController().navigate(R.id.action_splashScreenFragment_to_onboardingFragment)
        }, 1500)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}