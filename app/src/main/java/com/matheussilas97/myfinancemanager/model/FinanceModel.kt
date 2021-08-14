package com.matheussilas97.myfinancemanager.model

import java.math.BigDecimal
import java.util.*

data class FinanceModel(

    val id: Int?,

    val value: BigDecimal,

    val date: Date,

    val description: String,

    val situation: Boolean,

    val type: String
)
