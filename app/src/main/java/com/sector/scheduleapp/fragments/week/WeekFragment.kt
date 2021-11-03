package com.sector.scheduleapp.fragments.week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sector.scheduleapp.R
// import com.sector.scheduleapp.WeekViewModel
import com.sector.scheduleapp.databinding.FragmentWeekBinding
import com.sector.scheduleapp.objects.Day

class WeekFragment : Fragment() {
    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!

    private val days = mutableListOf(
        Day(title ="Понедельник"),
        Day(title ="Вторник"),
        Day(title ="Среда"),
        Day(title ="Четверг"),
        Day(title ="Пятница"),
        Day(title ="Суббота")
    )

    private val adapter = WeekAdapter(days)
    // private val viewModel: WeekViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() = with(binding) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun openSettingsFragment() {
        //findNavController().navigate()
    }
}