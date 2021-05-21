package id.ac.umn.icemoney.model

import id.ac.umn.icemoney.entity.Transaction
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.io.Serializable

data class TransactionChart (
    var name: String,
    var amount: Long = 0,
) : Serializable