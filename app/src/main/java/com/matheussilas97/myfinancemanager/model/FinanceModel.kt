package com.matheussilas97.myfinancemanager.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class FinanceModel(

    val id: String?,

    val idUser: String,

    val value: Double,

    val date: String,

    val description: String,

    val situation: Boolean,

    val type: String
) : Parcelable {

    constructor() : this("", "", 0.0, "", "", false, "")

}


