package id.ac.umn.icemoney.model

import org.threeten.bp.LocalDateTime

data class TransactionSummary (
    var income: Long,
    var expense: Long,
    var date: LocalDateTime,
)