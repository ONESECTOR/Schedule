package com.sector.scheduleapp.fragments.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.sector.scheduleapp.R
import com.sector.scheduleapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private var group: String? = null
    private var course: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBack.setOnClickListener {
                showHomeFragment()
            }

            binding.spinnerCourse.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position).toString()

                selectCourse(selected) // Выбираем курс
                fillOutSpinnerGroup() // После выбора курса сразу же заполняем второй спиннер группами относительно выбранного курса
            }

            binding.spinnerGroups.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position).toString()

                selectGroup(selected) // Выбираем группу
            }

            val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)

            course = prefs.getString("course", "Курс")
            binding.spinnerCourse.setText(course)

            if (isChanged()) { // Если мы хоть раз выбирали курс или группу, то здесь будет true, если будем делать это впервые то false
                binding.tilGroups.visibility = View.VISIBLE
                loadSettings() // Загружаем настройки
                fillOutSpinnerCourse() // Заполняем спиннер курсов
            } else {
                binding.tilGroups.visibility = View.INVISIBLE
                fillOutSpinnerCourse() // Заполняем спиннер курсов
            }
        }
    }

    private fun fillOutSpinnerGroup() {
        when (course) {
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

        binding.tilGroups.visibility = View.VISIBLE
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

    private fun selectCourse(selected: String) {
        course = selected
    }

    private fun selectGroup(selected: String) {
        group = selected

        saveSettings() // сохраняем настройки
    }

    private fun saveSettings() {
        val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.apply {
            putString("group", group)
            putString("course", course)
            putBoolean("is_changed", true)
            apply()
        }
    }

    private fun loadSettings() {
        val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)

        val courseText = prefs.getString("course", "Курс")
        val groupText = prefs.getString("group", "Группа")

        binding.spinnerGroups.setText(groupText) // Ставим в спиннер название группы

        when(courseText) { // Заполняем спиннер групп относительно курса
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

    private fun isChanged(): Boolean {
        val prefs = requireActivity().getSharedPreferences("my_settings", Context.MODE_PRIVATE)

        return prefs.getBoolean("is_changed", false)
    }

    private fun showHomeFragment() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}