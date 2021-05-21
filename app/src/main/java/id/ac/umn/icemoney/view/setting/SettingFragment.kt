package id.ac.umn.icemoney.view.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import id.ac.umn.icemoney.AboutUsActivity
import id.ac.umn.icemoney.LoginActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.view.home.TransactionViewModel
import id.ac.umn.icemoney.view.settings.SettingViewModel
import java.lang.Exception
import java.util.*

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
//        val textView: TextView = root.findViewById(R.id.text_setting)
        settingViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })

        // Ambil data dari Internet
        val ambilDataInetButton: Button = root.findViewById(R.id.ambilDataInet)
        ambilDataInetButton.setOnClickListener {view ->
            val listIds = mutableListOf<String>()
            transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
            transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
                // Kumpulkan list id dari DAO
                it.forEach {
                    val currentId = it.id
                    listIds.add(currentId)
                }
            })

            val id = FirebaseAuth.getInstance().currentUser.uid
            val database = FirebaseDatabase.getInstance().reference
            database.child(id).get().addOnSuccessListener {
                val dataCloud = it.children
                dataCloud.forEach{
                    Log.i("fullData",it.toString())
                    val currentIdCloud = it.child("id").value.toString()
                    Log.i("currentIdCloud",currentIdCloud)
                    Log.i("listId",listIds.toString())
                    // Cek apakah id cloud tidak sama dgn local
                    if(!listIds.contains(currentIdCloud)){
                        var trx = Transaction(
                            date = it.child("date").value.toString(),
                            amount = it.child("amount").value.toString().toLong(),
                            isIncome = it.child("income").value.toString().toBoolean(),
                            category = it.child("category").value.toString(),
                            paymentMethod = it.child("paymentMethod").value.toString(),
                            name = it.child("name").value.toString(),
                            id = it.child("id").value.toString()
                        )
                        Log.i("trxRetrieve", trx.toString())

                        // Add to DAO
                        transactionViewModel.addTransaction(trx)
                        // Snackbar notification
                        Snackbar.make(view, "Sukses mengupdate transaksi dan tersimpan di lokal.", Snackbar.LENGTH_SHORT)
                            .setAction("OK") { }.show()
                    }else{
                        // Snackbar notification
                        Snackbar.make(view, "Transaksi lokal sudah terbaru.", Snackbar.LENGTH_SHORT)
                            .setAction("OK") { }.show()
                    }
                }


            }.addOnFailureListener{
                // Snackbar notification
                Snackbar.make(view, "Gagal mengambil transaksi dari internet.", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }

        }

        // About Us
        val aboutUsButton: Button = root.findViewById(R.id.aboutUsButton)
        aboutUsButton.setOnClickListener {
            Intent(activity, AboutUsActivity::class.java).also {
                startActivity(it)
            }
        }

        // Logout
        val submitBtn: Button = root.findViewById(R.id.submitButton)
        submitBtn.setOnClickListener {
                view ->
            try{
                FirebaseAuth.getInstance().signOut()
                // Redirecting into Login page
                Intent(activity, LoginActivity::class.java).also{
                    startActivity(it)
                    activity?.finish()
                }
            }catch (e: Exception){
                Log.d("btnLogout", e.toString())

            }
        }

        return root
    }

}
