package id.ac.umn.icemoney.model

import java.util.Date

data class Transaction(
    val id: Long,
    val amount: Long,
    val category: String,
    val date: Date,
    val isIncome: Boolean = true,
    val name: String,
    val paymentMethod: String,
    val note: String? = ""
)
