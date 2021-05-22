package id.ac.umn.icemoney.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.model.TransactionSummary
import id.ac.umn.icemoney.adapter.TransactionMainAdapter.ViewHolder
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.utils.TransactionUtils
import kotlinx.android.synthetic.main.item_expense_date_amount.view.*

class TransactionMainAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var onItemSwipeListener: OnItemSwipeListener? = null

    var data : List<TransactionSummary> = listOf()
    val viewPool = RecyclerView.RecycledViewPool()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date = view.tvTransactionDate
        val yearMonth = view.tvTransactionYearMonth
        val day = view.tvTransactionDay
        val income = view.tvTransactionIncome
        val expense = view.tvTransactionExpense
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_expense_date_amount, parent, false
        ))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = data[position].date.dayOfMonth.toString()
        holder.day.text = data[position].date.dayOfWeek.toString().take(3)
        holder.yearMonth.text = "${data[position].date.year}/${data[position].date.month}"
        holder.income.text = "Rp. ${data[position].income}"
        holder.expense.text = "Rp. ${data[position].expense}"
        if (data[position].income == 0L) holder.income.visibility = View.GONE
        else holder.income.visibility = View.VISIBLE
        if (data[position].expense == 0L) holder.expense.visibility = View.GONE
        else holder.expense.visibility = View.VISIBLE
        holder.itemView.tag = position
        holder.itemView.setOnClickListener {  }
        val childAdapter = TransactionSubAdapter(data[position].data)

        val childLayoutManager = LinearLayoutManager(holder.itemView.rvSubItems.context,
            RecyclerView.VERTICAL, false)
        holder.itemView.rvSubItems.apply {
            layoutManager = childLayoutManager
            adapter = childAdapter
            setRecycledViewPool(viewPool)
            ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        if (viewHolder.adapterPosition < childAdapter.itemCount) {
                            val transaction = childAdapter.getTransactionByPosition(viewHolder.adapterPosition)
                            if (transaction != null) {
                                if (direction == ItemTouchHelper.LEFT) onItemSwipeListener?.deleteItem(transaction)
                                else onItemSwipeListener?.updateItem(transaction)
                                childAdapter.notifyDataSetChanged()
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            ).attachToRecyclerView(this)
        }
    }

    fun setDataList(list : List<Transaction>) {
        data = TransactionUtils.groupTransactionByDate(list)
        notifyDataSetChanged()
    }

    fun setOnItemSwipeListener(onItemSwipeListener: OnItemSwipeListener) {
        this.onItemSwipeListener = onItemSwipeListener
    }

    interface OnItemSwipeListener {
        fun updateItem(transaction: Transaction)
        fun deleteItem(transaction: Transaction)
    }
}