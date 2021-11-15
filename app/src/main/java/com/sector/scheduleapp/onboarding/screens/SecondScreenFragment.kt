package com.sector.scheduleapp.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.sector.scheduleapp.R
import com.sector.scheduleapp.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.btnNext.setOnClickListener {
            viewPager?.currentItem = 2
        }
    }

    override fun onResume() {
        super.onResume()

        fillOutSpinners()
        selectCourse()
        selectGroup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillOutSpinners() {
        val courses = resources.getStringArray(R.array.spinner_course)
        binding.spinnerCourse.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                courses
            )
        )

        val defaultGroups = resources.getStringArray(R.array.spinner_first_course)
        binding.spinnerGroups.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                defaultGroups
            )
        )
    }

    private fun selectCourse() {
        binding.spinnerCourse.setOnItemClickListener { parent, _, position, _ ->
            val selectedCourse = parent.getItemAtPosition(position).toString()

            when(selectedCourse) {
                "First" -> binding.spinnerGroups.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        resources.getStringArray(R.array.spinner_first_course)
                    )
                )
                "Second" -> binding.spinnerGroups.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        resources.getStringArray(R.array.spinner_second_course)
                    )
                )
                "Third" -> binding.spinnerGroups.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        resources.getStringArray(R.array.spinner_third_course)
                    )
                )
                "Fourth" -> binding.spinnerGroups.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        resources.getStringArray(R.array.spinner_fourth_course)
                    )
                )
            }
        }
    }

    private fun selectGroup() {
        binding.spinnerGroups.setOnItemClickListener { parent, _, position, _ ->
            val selectedGroup = parent.getItemAtPosition(position).toString()
            setGroup(selectedGroup)
        }
    }

    private fun setGroup(group: String) {
        val sharedPref = requireActivity().getSharedPreferences("Group", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("Selected group", group)
        editor.apply()
    }
}