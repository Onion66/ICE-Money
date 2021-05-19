package id.ac.umn.icemoney.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import kotlinx.android.synthetic.main.item_expense_date_amount.view.*
import kotlinx.android.synthetic.main.item_expense_detail.view.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class TransactionListAdapter(
//    private val transactions: List<Any>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val sorted: MutableList<Any> = mutableListOf()

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
        when (holder.itemViewType) {
            VIEW_TYPE_SUM -> (holder as TransactionDateHolder).bindView(sorted[position] as TransactionSummary)
            VIEW_TYPE_DETAIL -> (holder as TransactionDetailHolder).bindView(sorted[position] as Transaction)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (sorted[position] is TransactionSummary) {
            VIEW_TYPE_SUM
        } else {
            VIEW_TYPE_DETAIL
        }
    }

    override fun getItemCount(): Int = sorted.size

    fun setData(transactionList: List<Transaction>) {
        var mutableDate: LocalDateTime
        var count = 0
        var temp: TransactionSummary

        for ((idx, item) in transactionList.withIndex()) {
//            val str = "1986-04-08 12:30"
            var tempStr = item.date
            if(tempStr.length < 16){
                tempStr += " 00:00"
            }
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            mutableDate = LocalDateTime.parse(tempStr, formatter)
            Log.d("dateTest", mutableDate.toString())
            sorted.add(idx + count, TransactionSummary(0, 0, mutableDate))
            temp = sorted[idx + count] as TransactionSummary
            count = 0
            for ((i, trans) in transactionList.withIndex()) {
                var tempTransDate = trans.date
                if(tempTransDate.length < 16){
                    tempTransDate += " 00:00"
                }
//                Log.d("trans${i}", tempTransDate)
                if (mutableDate == LocalDateTime.parse(tempTransDate, formatter)) {
                    if (trans.isIncome) temp.income += trans.amount
                    else temp.expense += trans.amount
                    sorted.add(trans)
                    count += i
                }
            }
        }

        notifyDataSetChanged()
    }
}