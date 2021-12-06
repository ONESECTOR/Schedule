package com.sector.scheduleapp.objects

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Week(
    val day: String,
    val numberOfDay: String
): Parcelable