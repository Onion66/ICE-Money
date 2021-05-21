package id.ac.umn.icemoney.utils

import android.util.Log
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.model.TransactionChart
import id.ac.umn.icemoney.model.TransactionSummary
import id.ac.umn.icemoney.model.TransactionTotal
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object TransactionUtils {
    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val listKategori = listOf(
        "Makanan",
        "Minuman",
        "Transportasi",
        "Kesehatan",
        "Pendidikan",
        "Hiburan",
        "Lain-lain"
    )

    fun groupTransactionByDate(list : List<Transaction>) : List<TransactionSummary> {
        val groupByTime = list.groupBy { it.date.take(10) }
        val transaction = mutableListOf<TransactionSummary>()
        var expense: Long
        var income: Long

        val sortedReverse = groupByTime.toSortedMap(Comparator.reverseOrder())

        sortedReverse.mapValues { (date, data) ->
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

    fun getExpenseByMonth(list: List<Transaction>, month: String): List<TransactionChart>{
        val groupByMonth = list.groupBy { it.date.take(5).takeLast(2) }
        val listExpense = mutableListOf<TransactionChart>()
        Log.i("gbm",groupByMonth.toString())

        groupByMonth.mapValues { (date, data) ->
            data.forEach {
                if(!it.isIncome && date.take(5).takeLast(2) == month) listExpense.add(TransactionChart(it.category, it.amount))
            }
            Log.i("data",data.toString())
        }
        return listExpense
    }

    fun getTotalExpense(list : List<Transaction>) : TransactionTotal {
        var expense : Long = 0
        var income : Long = 0
        list.forEach {
            if (it.isIncome) income += it.amount
            else expense += it.amount
        }
        return TransactionTotal(
            expense,
            income,
            (income - expense)
        )
    }
}