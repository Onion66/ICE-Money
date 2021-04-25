package id.ac.umn.icemoney.model

import org.threeten.bp.LocalDateTime

data class Transaction(
    val id: Long,
    val amount: Long,
    val category: String,
    val date: LocalDateTime,
    val isIncome: Boolean = true,
    val name: String,
    val paymentMethod: String,
    val note: String? = ""
)
