package com.androidcourse.reminderlevel4kotlin

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reminder(
    var reminder: String
) : Parcelable