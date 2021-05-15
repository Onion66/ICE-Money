package id.ac.umn.icemoney.model

import org.threeten.bp.LocalDateTime

data class Transaction(
    var id: Long,
    var amount: Long,
    var category: String,
    var date: String,
    var isIncome: Boolean = true,
    var name: String,
    var paymentMethod: String,
    var note: String? = ""
)
