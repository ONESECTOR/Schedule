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

    private var courseText: String? = null

    private var itemClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDefaultSettings()

        binding.apply {
            btnNext.setOnClickListener {
                groupSelected(itemClicked)
            }

            spinnerCourse.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position).toString()

                selectCourse(selected)
            }

            spinnerGroups.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position).toString()
                itemClicked = true

                saveSettings(selected)
            }
        }
    }

    private fun groupSelected(selected: Boolean) {
        if (selected) {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
            viewPager?.currentItem = 2
        } else {
            Toast.makeText(requireContext(), "Вы не выбрали группу!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectCourse(course: String){
        binding.tilGroups.visibility = View.VISIBLE

        courseText = course

        when(courseText) {
            "Первый" -> {
                binding.spinnerGroups.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        resources.getStringArray(R.array.spinner_first_course)
                    )
                )
            }
            "Второй" -> binding.spinnerGroups.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item,
                    resources.getStringArray(R.array.spinner_second_course)
                )
            )
            "Третий" -> binding.spinnerGroups.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item,
                    resources.getStringArray(R.array.spinner_third_course)
                )
            )
            "Четвертый" -> binding.spinnerGroups.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.dropdown_item,
                    resources.getStringArray(R.array.spinner_fourth_course)
                )
            )
        }
    }

    private fun saveSettings(group: String) {
        val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.apply {
            putString("course", courseText)
            putString("group", group)
            putBoolean("is_changed", true)
            apply()
        }
    }

    private fun setDefaultSettings() {
        binding.apply {
            tilGroups.visibility = View.INVISIBLE
        }

        fillOutSpinnerCourse()
    }

    private fun fillOutSpinnerCourse() {
        val courses = resources.getStringArray(R.array.spinner_course)

        binding.spinnerCourse.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                courses
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}