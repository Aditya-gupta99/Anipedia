package com.sparklead.anipedia.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sparklead.anipedia.R
import com.sparklead.anipedia.databinding.FragmentOnboardingBinding
import com.sparklead.anipedia.model.OnboardingItem
import com.sparklead.anipedia.ui.adapter.IntroViewPagerAdapter

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingAdapter: IntroViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)

        val navBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.visibility = View.GONE

        setOnboardingItem()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setOnboardingItem() {

        onboardingAdapter = IntroViewPagerAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.intro_first,
                    title = "Welcome To Anipedia",
                    description = "Your Ultimate Guide to Explore, Discover, and Immerse in Every Detail!"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.intro_second,
                    title = "Favorite Anime",
                    description = "Unlock the ultimate anime journey by saving your favorite shows!"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.intro_third,
                    title = "Offline support",
                    description = "You can enjoy your favorite anime even offline!"
                )
            )
        )

        val onboardingViewPager = binding.onBoardingViewPager
        onboardingViewPager.adapter = onboardingAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        binding.btnNext.setOnClickListener {
            if (onboardingViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
            } else {
                findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {

        binding.progressCircular.setProgress((((position + 1).toFloat()) / 3) * 100, true, 500L)
    }
}