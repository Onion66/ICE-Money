package id.ac.umn.icemoney.model

import org.threeten.bp.LocalDateTime

data class TransactionAdapter(
    var income: Long,
    var expense: Long,
    var date: LocalDateTime,
    var data: MutableList<Transaction>
)
