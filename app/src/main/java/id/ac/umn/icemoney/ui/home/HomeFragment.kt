package id.ac.umn.icemoney.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.threetenabp.AndroidThreeTen
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.adapter.TransactionListAdapter
import id.ac.umn.icemoney.model.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import kotlinx.android.synthetic.main.fragment_home.*
import org.threeten.bp.LocalDateTime

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidThreeTen.init(context)
        if(context != null) {
            rvTransactionList.apply {
                val transactions = listOf(
                    Transaction(
                        1,
                        20000,
                        "Food",
                        LocalDateTime.of(1990, 12, 31, 23, 59, 59),
                        false,
                        "Cheeseburger",
                        "Direct"
                    ),
                    Transaction(
                        2,
                        9000,
                        "Food",
                        LocalDateTime.of(1990, 12, 30, 23, 59, 59),
                        false,
                        "Burger",
                        "Direct"
                    ),
                    Transaction(
                        3,
                        102000,
                        "Sales",
                        LocalDateTime.of(1990, 12, 31, 23, 59, 59),
                        true,
                        "Selling Account",
                        "Bank"
                    ),
                    Transaction(
                        4,
                        18000,
                        "Food",
                        LocalDateTime.of(1990, 12, 30, 23, 59, 59),
                        false,
                        "Hotdog",
                        "Direct"
                    )
                )
                // Failed
//                var totalIncome: Long = 0
//                var totalExpense: Long = 0
//                val transaction = transactions.toMutableList()
//                val temp = TransactionAdapter(0, 0, LocalDateTime.now(), mutableListOf())
//                val sorted: MutableList<TransactionAdapter> = mutableListOf()
//                val data: MutableList<Transaction> = mutableListOf()
//
//                for ((i, item) in transaction.withIndex()) {
//                    temp.date = item.date
//                    for ((idx, trans) in transaction.withIndex()) {
//                        if (temp.date == trans.date) {
//                            if (item.isIncome) totalIncome += item.amount
//                            else totalExpense += item.amount
//                            data.add(item)
//                        }
//                    }
//                    temp.data = data
//                    temp.expense = totalExpense
//                    temp.income = totalIncome
//                    sorted.add(temp)
//                }

                // Successful
                val sorted: MutableList<Any> = mutableListOf()
                var mutableDate: LocalDateTime
                var count = 0
                var temp: TransactionSummary

                for ((idx, item) in transactions.withIndex()) {
                    mutableDate = item.date
                    sorted.add(idx + count, TransactionSummary(0, 0, mutableDate))
                    temp = sorted[idx + count] as TransactionSummary
                    count = 0
                    for ((i, trans) in transactions.withIndex()) {
                        if (mutableDate == trans.date) {
                            if (trans.isIncome) temp.income += trans.amount
                            else temp.expense += trans.amount
                            sorted.add(trans)
                            count += i
                        }
                    }
                }

                    // Successful
//                val sorted: MutableList<Any> = mutableListOf()
//                var mutableDate: LocalDateTime
//                var count = 0
//                var totalIncome: Long = 0
//                var totalExpense: Long = 0
//
//                for ((idx, item) in transactions.withIndex()) {
//                    mutableDate = item.date
//                    for ((i, trans) in transactions.withIndex()) {
//                        if (mutableDate == trans.date) {
//                            if (trans.isIncome) totalIncome += trans.amount
//                            else totalExpense += trans.amount
//                            sorted.add(trans)
//                            count += i
//                        }
//                    }
//                    sorted.add(idx + count, TransactionSummary(totalIncome, totalExpense, mutableDate))
//                    count = 0
//                    totalIncome = 0
//                    totalExpense = 0
//                }

                // Failed
//                for ((i, item) in transaction.withIndex()) {
//                    mutableDate = item.date
//                    sorted.add(TransactionSummary(0, 0, mutableDate))
//                    for ((idx, trans) in transaction.withIndex()) {
//                        if (mutableDate == trans.date) {
//                            if (item.isIncome) totalIncome += item.amount
//                            else totalExpense += item.amount
//                            sorted.add(item)
//                            count += 1
//                        }
//                    }
//                    if (sorted[i] is TransactionSummary) {
//                        (sorted[i] as TransactionSummary).expense = totalExpense
//                        (sorted[i] as TransactionSummary).income = totalIncome
//                    }
//                }

                layoutManager = LinearLayoutManager(activity)
                adapter = TransactionListAdapter(sorted.distinct())
//                adapter = TransactionListAdapter(sorted.distinct().sortedByDescending {
//                    if (it is TransactionSummary) it.date
//                    else (it as Transaction).date
//                })
            }
        }
    }
}