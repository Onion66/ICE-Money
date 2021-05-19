package id.ac.umn.icemoney.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.threetenabp.AndroidThreeTen
import id.ac.umn.icemoney.AddTransactionActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.adapter.TransactionListAdapter
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.model.TransactionSummary
import kotlinx.android.synthetic.main.fragment_home.*
import org.threeten.bp.LocalDateTime

class HomeFragment : Fragment() {
    private lateinit var transactionList: List<Transaction>
//    private lateinit var homeViewModel: HomeViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    val sorted: MutableList<Any> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUICallBack()
        val adapter = TransactionListAdapter()
        rvTransactionList.adapter = adapter
        rvTransactionList.layoutManager = LinearLayoutManager(requireContext())

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
        AndroidThreeTen.init(context)
//        if(context != null) {
//            Log.i("testDate",LocalDateTime.of(1990, 12, 31, 23, 59, 59).toString())
//            rvTransactionList.apply {
//                // Test RecyclerView
//                var transactions = listOf(
//                    Transaction(
//                        1,
//                        20000,
//                        "Food",
//                        LocalDateTime.of(2021, 5, 14, 23, 59, 59).toString(),
//                        false,
//                        "Cheeseburger",
//                        "Direct"
//                    ),
//                    Transaction(
//                        2,
//                        9000,
//                        "Food",
//                        LocalDateTime.of(2021, 5, 13, 23, 59, 59).toString(),
//                        false,
//                        "Burger",
//                        "Direct"
//                    ),
//                    Transaction(
//                        3,
//                        102000,
//                        "Sales",
//                        LocalDateTime.of(2021, 5, 14, 23, 59, 59).toString(),
//                        true,
//                        "Selling Account",
//                        "Bank"
//                    ),
//                    Transaction(
//                        4,
//                        18000,
//                        "Food",
//                        LocalDateTime.of(2021, 5, 13, 23, 59, 59).toString(),
//                        false,
//                        "Hotdog",
//                        "Direct"
//                    )
//                )
////                transactions = listOf()
//
////                val sorted: MutableList<Any> = mutableListOf()
//                var mutableDate: LocalDateTime
//                var count = 0
//                var temp: TransactionSummary
//
//                if(transactions.isEmpty()){
//                    //if data isn't available, show the empty text
//                    noData.visibility = View.VISIBLE
//                }else{
//                    //if data is available, don't show the empty text
//                    noData.visibility = View.GONE
//
//                    for ((idx, item) in transactions.withIndex()) {
//                        mutableDate = LocalDateTime.parse(item.date)
//                        sorted.add(idx + count, TransactionSummary(0, 0, mutableDate))
//                        temp = sorted[idx + count] as TransactionSummary
//                        count = 0
//                        for ((i, trans) in transactions.withIndex()) {
//                            if (mutableDate == LocalDateTime.parse(trans.date)) {
//                                if (trans.isIncome) temp.income += trans.amount
//                                else temp.expense += trans.amount
//                                sorted.add(trans)
//                                count += i
//                            }
//                        }
//                    }
//                    layoutManager = LinearLayoutManager(activity)
////                    adapter = TransactionListAdapter(sorted.distinct())
//                }
//
//            }
//        }
    }

    private fun initUICallBack() {
        fabAddTransaction.setOnClickListener {
            activity?.startActivity(Intent(activity, AddTransactionActivity::class.java))
        }
    }
}