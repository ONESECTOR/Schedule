package com.sector.scheduleapp.fragments.week

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sector.scheduleapp.R
import com.sector.scheduleapp.databinding.FragmentWeekBinding
import com.sector.scheduleapp.fragments.week.adapters.AdvantagesAdapter
import com.sector.scheduleapp.fragments.week.adapters.WeekAdapter
import com.sector.scheduleapp.fragments.week.viewmodel.WeekViewModel
import com.sector.scheduleapp.objects.Advantage
import com.sector.scheduleapp.objects.Week
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class WeekFragment : Fragment() {
    private var _binding: FragmentWeekBinding? = null
    private val binding get() = _binding!!

    private lateinit var advantagesList: MutableList<Advantage>
    private lateinit var daysList: MutableList<Week>

    private val viewModel: WeekViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        advantagesList = mutableListOf(
            Advantage(
                title = resources.getString(R.string.advantage_title),
                description = resources.getString(R.string.advantage_description),
                symbol = resources.getString(R.string.advantage_symbol_information)
            )
        )

        daysList = mutableListOf(
            Week(day = "Понедельник", numberOfDay = "1"),
            Week(day = "Вторник", numberOfDay = "2"),
            Week(day = "Среда", numberOfDay = "3"),
            Week(day = "Четверг", numberOfDay = "4"),
            Week(day = "Пятница", numberOfDay = "5"),
            Week(day = "Суббота", numberOfDay = "6")
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdvantages()
        setupRecyclerViewDays()
        openSettingsFragment()

        // Observe LiveData (type of week)
        viewModel.weekType.observe(viewLifecycleOwner, Observer { type ->
            binding.tvTypeOfWeek.text = type.toString()

            val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)

            val editor = prefs.edit()
            editor.putString("mode", type.toString())
            editor.apply()
        })

        // Observe LiveData (day of week)
        viewModel.currentDate.observe(viewLifecycleOwner, Observer { day ->
            binding.tvCurrentDate.text = day.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerViewDays() = with(binding) {
        val adapter = WeekAdapter(daysList)
        binding.apply {
            rvDays.layoutManager = LinearLayoutManager(requireContext())
            rvDays.adapter = adapter
            rvDays.itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
    }

    private fun setupRecyclerViewAdvantages() = with(binding) {
        val adapter = AdvantagesAdapter(advantagesList)
        binding.apply {
            rvAdvantage.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvAdvantage.adapter = adapter
            rvAdvantage.itemAnimator = SlideInLeftAnimator().apply {
                addDuration = 300
            }
        }
    }

    private fun openSettingsFragment() {
        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_weekFragment_to_settingsFragment)
        }
    }
}