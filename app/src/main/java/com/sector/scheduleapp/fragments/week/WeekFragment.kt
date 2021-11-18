package com.sector.scheduleapp.fragments.week

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sector.scheduleapp.R
import com.sector.scheduleapp.databinding.FragmentWeekBinding
import com.sector.scheduleapp.objects.Advantage
import com.sector.scheduleapp.objects.Week
import java.time.LocalDate

class WeekFragment : Fragment() {
    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!

    private val daysOfWeek = mutableListOf(
        Week(day = "Понедельник"),
        Week(day = "Вторник"),
        Week(day = "Среда"),
        Week(day = "Четверг"),
        Week(day = "Пятница"),
        Week(day = "Суббота")
    )

    private val advantagesList = mutableListOf(
        Advantage(title = "Расписание", description = "Всегда под рукой!")
    )

    private val viewModel: WeekViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdvantages()
        setupRecyclerViewDays()
        openSettingsFragment()

        viewModel.weekType.observe(viewLifecycleOwner, Observer { type ->
            binding.tvTypeOfWeek.text = type.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerViewDays() = with(binding) {
        val adapter = WeekAdapter(daysOfWeek)
        binding.rvDays.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvDays.adapter = adapter
    }

    private fun setupRecyclerViewAdvantages() = with(binding) {
        val adapter = AdvantagesAdapter(advantagesList)
        binding.rvAdvantage.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAdvantage.adapter = adapter
    }

    private fun openSettingsFragment() {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_weekFragment_to_settingsFragment)
        }
    }
}