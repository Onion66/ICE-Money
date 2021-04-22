package id.ac.umn.icemoney.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.adapter.TransactionListAdapter
import id.ac.umn.icemoney.model.Transaction
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvTransactionList.apply {
            layoutManager = LinearLayoutManager(this@HomeFragment.activity)
            adapter = TransactionListAdapter(
                listOf(
                    Transaction(
                        1,
                        9000,
                        "Food",
                        Date(1990, 1, 1),
                        false,
                        "Burger",
                        "Direct"
                    ),
                    Transaction(
                        2,
                        50000,
                        "Fund",
                        Date(2000, 1, 1),
                        true,
                        "Weekly Saving",
                        "Bank"
                    )
                )
            )
        }
    }
}