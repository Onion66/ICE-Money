package id.ac.umn.icemoney.view.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.threetenabp.AndroidThreeTen
import id.ac.umn.icemoney.AddTransactionActivity
import id.ac.umn.icemoney.LoginActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.UpdateTransactionActivity
import id.ac.umn.icemoney.adapter.TransactionMainAdapter
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.utils.NotificationUtils
import id.ac.umn.icemoney.utils.TransactionUtils
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Math.abs

class HomeFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel
    var transactionAdapter: TransactionMainAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUICallBack()

        transactionAdapter = TransactionMainAdapter()
        if (rvTransactionList != null) {
            rvTransactionList.layoutManager = LinearLayoutManager(context)
            rvTransactionList.adapter = transactionAdapter
        }

        transactionAdapter?.setOnItemSwipeListener(object : TransactionMainAdapter.OnItemSwipeListener {
            override fun updateItem(transaction: Transaction) {
                startActivityForResult(Intent(activity, UpdateTransactionActivity::class.java)
                    .putExtra("data", transaction), 2)
            }

            override fun deleteItem(transaction: Transaction) {
                transactionViewModel.deleteTransaction(transaction)
                transactionAdapter?.notifyDataSetChanged()
            }
        })

        transactionViewModel.transactionList.observe(viewLifecycleOwner, {
            transactionAdapter?.setDataList(it)

            // No Data Image
            if(transactionAdapter?.itemCount == 0){
                //if data isn't available, show the empty text
                noData.visibility = View.VISIBLE
            }else{
                //if data is available, don't show the empty text
                noData.visibility = View.GONE
            }

            val transaction = TransactionUtils.getTotalExpense(it)

            // Notification nilai pengeluaran dan pemasukan
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            NotificationUtils.sendNotification(transaction.total, intent, requireContext())

            // Set nilai pengeluaran dan pemasukan
            tvTransactionTotalExpense.text = "Rp. ${transaction.expense}"
            tvTransactionTotalIncome.text = "Rp. ${transaction.income}"
            if (transaction.total < 0) tvTransactionTotal.setTextColor(Color.parseColor("#E74C3C"))
            else tvTransactionTotal.setTextColor(Color.parseColor("#3498DB"))
            tvTransactionTotal.text = "Rp. ${abs(transaction.total)}"
            transactionAdapter?.notifyDataSetChanged()
        })

//        transactionViewModel.transactions.observe(viewLifecycleOwner, {
//            adapter.setDataList(it)
//            val transaction = TransactionUtils.getTotalExpense(it)
//            tvTransactionTotalExpense.text = "Rp. ${transaction.expense}"
//            tvTransactionTotalIncome.text = "Rp. ${transaction.income}"
//            if (transaction.total < 0) tvTransactionTotal.setTextColor(Color.parseColor("#E74C3C"))
//            else tvTransactionTotal.setTextColor(Color.parseColor("#3498DB"))
//            tvTransactionTotal.text = "Rp. ${abs(transaction.total)}"
//        })

        AndroidThreeTen.init(context)
    }

    private fun initUICallBack() {
        fabAddTransaction.setOnClickListener {
            startActivityForResult(Intent(activity, AddTransactionActivity::class.java), 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            transactionViewModel.deleteAll()
        }
        builder.setNegativeButton("No"){_,_ ->
        }
        builder.setTitle("Hapus data lokal")
        builder.setMessage("Apakah Anda yakin?")
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val transaction = data?.getSerializableExtra("data")
            if (requestCode == 1) transactionViewModel.addTransaction(transaction as Transaction)
            else if (requestCode == 2) transactionViewModel.updateTransaction(transaction as Transaction)
            rvTransactionList.adapter?.notifyDataSetChanged()
        }
    }
}