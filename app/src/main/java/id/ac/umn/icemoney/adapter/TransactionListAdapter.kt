package id.ac.umn.icemoney.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.model.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import kotlinx.android.synthetic.main.item_expense_date_amount.view.*
import kotlinx.android.synthetic.main.item_expense_detail.view.*
import java.lang.IllegalArgumentException

class TransactionListAdapter(
    private val transactions: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_SUM = 1
        const val VIEW_TYPE_DETAIL = 2
    }

    class TransactionDateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val transDate = view.tvTransactionDate
        private val transYearMonth = view.tvTransactionYearMonth
        private val transDay = view.tvTransactionDay
        private val transIn = view.tvTransactionIncome
        private val transOut = view.tvTransactionExpense

        fun bindView(transaction: TransactionSummary) {
            transDate.text = transaction.date.dayOfMonth.toString()
            transYearMonth.text = "${ transaction.date.year } \\ ${ transaction.date.monthValue }"
            transDay.text = transaction.date.dayOfWeek.toString().take(3)
            transIn.text = "Rp. ${ transaction.income }"
            transOut.text = "Rp. ${ transaction.expense }"
            if (transaction.income == 0.toLong()) transIn.visibility = View.GONE
            else transIn.visibility = View.VISIBLE
            if (transaction.expense == 0.toLong()) transOut.visibility = View.GONE
            else transOut.visibility = View.VISIBLE
//            if (transaction.isIncome) {
//                transIn.text = "Rp." + transaction.amount.toString()
//                transOut.text = "Rp. 0"
//            } else {
//                transOut.text = "Rp." + transaction.amount.toString()
//                transIn.text = "Rp. 0"
//            }
        }
    }

    class TransactionDetailHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val transItemName = view.tvTransactionItemName
        private val transCategory = view.tvTransactionCategory
        private val transPaymentMethod = view.tvTransactionPaymentMethod
        private val transAmount = view.tvTransactionItemAmount

        fun bindView(transaction: Transaction) {
            transItemName.text = transaction.name
            transCategory.text = transaction.category
            transPaymentMethod.text = transaction.paymentMethod
            transAmount.text = "Rp. ${ transaction.amount }"
            if (transaction.isIncome) transAmount.setTextColor(Color.parseColor("#3498DB"))
            else transAmount.setTextColor(Color.parseColor("#E74C3C"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SUM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_expense_date_amount, parent, false)
                TransactionDateHolder(view)
            }
            VIEW_TYPE_DETAIL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_expense_detail, parent, false)
                TransactionDetailHolder(view)
            }
            else -> {
                throw IllegalArgumentException("Invalid ViewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


//        var date: LocalDateTime
//        var income: Long = 0
//        var expense: Long = 0
//        for ((idx, list) in transaction.withIndex()) {
//            date = list[idx].date
//            for (item in list) {
//                if (item.isIncome) income += item.amount
//                else expense += item.amount
//                (holder as TransactionDetailHolder).bindView(item)
//            }
//            (holder as TransactionDateHolder).bindView(income, expense, date)
//            income = 0
//            expense = 0
//        }

        when (holder.itemViewType) {
            VIEW_TYPE_SUM -> (holder as TransactionDateHolder).bindView(transactions[position] as TransactionSummary)
            VIEW_TYPE_DETAIL -> (holder as TransactionDetailHolder).bindView(transactions[position] as Transaction)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (transactions[position] is TransactionSummary) {
            VIEW_TYPE_SUM
        } else {
            VIEW_TYPE_DETAIL
        }
    }

    override fun getItemCount(): Int = transactions.size
}