package com.sector.scheduleapp.fragments.week.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.*

class WeekViewModel: ViewModel() {
    companion object {
        private val WEEK_TYPE_BASELINE_DAY = LocalDate.of(2021, 9, 1)

        private const val NUMERATOR = "Числитель"
        private const val DENOMINATOR = "Знаменатель"
    }

    private val mutableWeekType = MutableLiveData<String>()
    val weekType: LiveData<String> get() = mutableWeekType

    private val mutableCurrentDay = MutableLiveData<String>()
    val currentDate: LiveData<String> get() = mutableCurrentDay

    init {
        updateCurrentWeekType()
        getCurrentDay()
    }

    private fun getCurrentDay() {
        val date = Date()
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())

        val currentDay = dateFormat.format(date)
        mutableCurrentDay.value = currentDay
    }

    private fun updateCurrentWeekType() {
        val today = LocalDate.now()
        val weeks = ChronoUnit.WEEKS.between(WEEK_TYPE_BASELINE_DAY, today)

        if (weeks % 2 == 0L) {
            mutableWeekType.value = NUMERATOR
        } else {
            mutableWeekType.value = DENOMINATOR
        }

        // In case week changes while this ViewModel is alive, queue next update.
        val nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        val nextMondayMidnight = LocalDateTime.of(nextMonday, LocalTime.MIDNIGHT)

        viewModelScope.launch {
            delay(ChronoUnit.MILLIS.between(LocalDateTime.now(), nextMondayMidnight))
            updateCurrentWeekType()
        }
    }
}