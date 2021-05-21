package id.ac.umn.icemoney.view.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.tbruyelle.rxpermissions2.RxPermissions
import id.ac.umn.icemoney.AboutUsActivity
import id.ac.umn.icemoney.LoginActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.view.home.TransactionViewModel


class SettingFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        val database = FirebaseDatabase.getInstance().reference
        val id = FirebaseAuth.getInstance().currentUser.uid

        // Set Image User
        val gambarUserImage: ImageView = root.findViewById(R.id.gambarUser)
        database.child("gambarProfile").child(id).get().addOnSuccessListener {
            Log.i("urlGambar", it.value.toString())
            Log.i("id", id)
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
            Glide.with(this).load(it.value).apply(options).into(gambarUserImage)
        }

        // Set Email User
        val emailUserText: TextView = root.findViewById(R.id.emailUser)
        emailUserText.text = FirebaseAuth.getInstance().currentUser.email

        // Ganti Gambar
        val idFirebase = database.push().key
        val gantiGambarButton: Button = root.findViewById(R.id.gantiGambar)
        gantiGambarButton.setOnClickListener { view ->


            // TODO: Simpan data ke IMGUR dan return URL
            val urlImage = "test"

            // Simpan ke firebase
            database.child("gambarProfile").child(id).child(idFirebase!!).setValue(urlImage).addOnSuccessListener {
                // Snackbar notification
                Snackbar.make(view, "Sukses menyimpan gambar", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }.addOnFailureListener{
                // Snackbar notification
                Snackbar.make(view, "Gagal menyimpan gambar ke internet", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
        }


        // Ambil data dari Internet
        val ambilDataInetButton: Button = root.findViewById(R.id.ambilDataInet)
        ambilDataInetButton.setOnClickListener { view ->
            val listIds = mutableListOf<String>()
            transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
            transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
                // Kumpulkan list id dari DAO
                it.forEach {
                    val currentId = it.id
                    listIds.add(currentId)
                }
            })

            database.child("transaksi").child(id).get().addOnSuccessListener {
                val dataCloud = it.children
                dataCloud.forEach{
                    Log.i("fullData", it.toString())
                    val currentIdCloud = it.child("id").value.toString()
                    Log.i("currentIdCloud", currentIdCloud)
                    Log.i("listId", listIds.toString())
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
                        Snackbar.make(
                            view,
                            "Sukses mengupdate transaksi dan tersimpan di lokal.",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("OK") { }.show()
                    }else{
                        // Snackbar notification
                        Snackbar.make(view, "Transaksi lokal sudah terbaru.", Snackbar.LENGTH_SHORT)
                            .setAction("OK") { }.show()
                    }
                }


            }.addOnFailureListener{
                // Snackbar notification
                Snackbar.make(
                    view,
                    "Gagal mengambil transaksi dari internet.",
                    Snackbar.LENGTH_SHORT
                )
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
        submitBtn.setOnClickListener { view ->
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
