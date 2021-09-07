package com.matheussilas97.myfinancemanager.model

import java.io.Serializable

data class FinanceModel(

    val id: String?,

    val idUser: String,

    val value: Double,

    val date: String,

    val description: String,

    val situation: Boolean,

    val type: String

) : Serializable {

    constructor() : this("", "", 0.0, "", "", false, "")

}


