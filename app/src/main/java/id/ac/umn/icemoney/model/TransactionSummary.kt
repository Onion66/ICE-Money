package id.ac.umn.icemoney.model

import id.ac.umn.icemoney.entity.Transaction
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.io.Serializable

data class TransactionSummary (
    var income: Long = 0,
    var expense: Long = 0,
    var date: LocalDate = LocalDate.now(),
    var data: List<Transaction> = mutableListOf(),
) : Serializable