package com.sector.scheduleapp.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sector.scheduleapp.databinding.FragmentViewPagerBinding
import com.sector.scheduleapp.onboarding.adapters.ViewPagerAdapter
import com.sector.scheduleapp.onboarding.screens.FirstScreenFragment
import com.sector.scheduleapp.onboarding.screens.SecondScreenFragment
import com.sector.scheduleapp.onboarding.screens.ThirdScreenFragment

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(
            FirstScreenFragment(),
            SecondScreenFragment(),
            ThirdScreenFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        return binding.root
    }
}