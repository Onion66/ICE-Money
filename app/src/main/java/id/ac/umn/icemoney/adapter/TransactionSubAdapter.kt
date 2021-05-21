package id.ac.umn.icemoney.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.adapter.TransactionSubAdapter.ViewHolder
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import kotlinx.android.synthetic.main.item_expense_detail.view.*

class TransactionSubAdapter(
    val transactions : List<Transaction>
) : RecyclerView.Adapter<ViewHolder>() {
    var data : List<Transaction> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.tvTransactionItemName
        val amount = view.tvTransactionItemAmount
        val payment = view.tvTransactionPaymentMethod
        val category = view.tvTransactionCategory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_expense_detail, parent, false
            )
        )
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = transactions[position].name
        holder.amount.text = "Rp. ${transactions[position].amount}"
        holder.payment.text = transactions[position].paymentMethod
        holder.category.text = transactions[position].category
        if (transactions[position].isIncome) holder.amount.setTextColor(Color.parseColor("#3498DB"))
        else holder.amount.setTextColor(Color.parseColor("#E74C3C"))
    }

    fun getTransactionByPosition(position: Int) : Transaction = transactions[position]

    fun setDataList(list : List<Transaction>) {
        data = list
        notifyDataSetChanged()
    }
}