package id.ac.umn.icemoney.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.model.Transaction
import kotlinx.android.synthetic.main.item_expense_date_amount.view.*
import kotlinx.android.synthetic.main.item_expense_detail.view.*

class TransactionListAdapter(
    private val transaction: List<Transaction>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class TransactionDateHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val transDate = view.tvTransactionDate
        private val transYearMonth = view.tvTransactionYearMonth
        private val transDay = view.tvTransactionDay
        private val transIn = view.tvTransactionIncome
        private val transOut = view.tvTransactionOutcome

        fun bindView(transaction: Transaction) {
            transDate.text = transaction.date.date.toString()
            transYearMonth.text = transaction.date.year.toString() + '/' + transaction.date.month.toString()
            transDay.text = transaction.date.day.toString()
            if (transaction.isIncome) {
                transIn.text = "Rp." + transaction.amount.toString()
                transOut.text = "Rp. 0"
            } else {
                transOut.text = "Rp." + transaction.amount.toString()
                transIn.text = "Rp. 0"
            }
        }
    }

    class TransactionDetailHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val transItemName = view.tvTransactionItemName
        private val transCategory = view.tvTransactionCategory
        private val transPaymentMethod = view.tvTransactionPaymentMethod
        private val transOut = view.tvTransactionItemOutcome

        fun bindView(transaction: Transaction) {
            transItemName.text = transaction.name
            transCategory.text = transaction.category
            transPaymentMethod.text = transaction.paymentMethod
            transOut.text = transaction.amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionDetailHolder {
        return TransactionDetailHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense_date_amount, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> (holder as TransactionDateHolder).bindView(transaction[position])
            1 -> (holder as TransactionDetailHolder).bindView(transaction[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2 * 2
    }

    override fun getItemCount(): Int = transaction.size
}