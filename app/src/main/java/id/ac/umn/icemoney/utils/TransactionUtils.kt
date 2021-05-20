package id.ac.umn.icemoney.utils

import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object TransactionUtils {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    fun groupTransactionByDate(list : List<Transaction>) : List<TransactionSummary> {
        val groupByTime = list.groupBy { it.date.take(10) }
        val transaction = mutableListOf<TransactionSummary>()
        var expense: Long
        var income: Long

        groupByTime.mapValues { (date, data) ->
            expense = 0
            income = 0
            data.forEach {
                if (it.isIncome) income += it.amount
                else expense += it.amount
            }
            transaction.add(TransactionSummary(
                income,
                expense,
                LocalDate.parse(date, dateFormatter),
                data
            ))
        }
        return transaction
    }
}