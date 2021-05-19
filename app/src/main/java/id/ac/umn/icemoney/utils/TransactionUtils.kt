package id.ac.umn.icemoney.utils

import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object TransactionUtils {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")

    fun groupTransactionByDate(list : List<Transaction>) : List<TransactionSummary> {
        val groupByTime = list.groupBy { it.date }
        val transaction = mutableListOf<TransactionSummary>()
        var temp = TransactionSummary()

        groupByTime.mapValues { (date, data) ->
            temp.expense = 0
            temp.income = 0
            temp.data = listOf()
            temp = TransactionSummary(
                date = LocalDateTime.parse(date, formatter),
                data = data
            )
            data.forEach {
                if (it.isIncome) temp.income += it.amount
                else temp.expense += it.amount
            }
            transaction.add(temp)
        }
        return transaction
    }
}