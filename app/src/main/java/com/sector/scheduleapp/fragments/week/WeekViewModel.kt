package com.sector.scheduleapp.fragments.week

import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

class WeekViewModel: ViewModel() {
    companion object {
        private val WEEK_TYPE_BASELINE_DAY = LocalDate.of(2021, 11, 8)

        private const val NUMERATOR = "Числитель"
        private const val DENOMINATOR = "Знаменатель"
    }

    private val mutableWeekType = MutableLiveData<String>()
    val weekType: LiveData<String> get() = mutableWeekType

    init {
        updateCurrentWeekType()
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