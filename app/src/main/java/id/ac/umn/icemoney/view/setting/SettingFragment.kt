package id.ac.umn.icemoney.view.setting

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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
import id.ac.umn.icemoney.AboutUsActivity
import id.ac.umn.icemoney.LoginActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.entity.Transaction
import id.ac.umn.icemoney.view.home.TransactionViewModel
//import id.ac.umn.icemoney.view.settings.SettingViewModel
import java.lang.Exception
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SettingFragment : Fragment() {
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_VIDEO_CAPTURE = 2

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var gambarUserImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        val database = FirebaseDatabase.getInstance().reference
        val id = FirebaseAuth.getInstance().currentUser.uid

        // Set Image User
        gambarUserImage = root.findViewById(R.id.gambarUser)
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
        val gantiGambarButton: Button = root.findViewById(R.id.gantiGambar)
        gantiGambarButton.setOnClickListener { view ->
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                if(checkCameraPermission() == true){
                    if(it.resolveActivity(requireActivity().packageManager) != null){
                        startActivityForResult(it, REQUEST_IMAGE_CAPTURE)
                    }
                }
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
                if(it.exists()){
                    val dataCloud = it.children
                    dataCloud.forEach{
                        Log.i("fullData", it.toString())
                        val currentIdCloud = it.child("id").value.toString()
                        Log.i("currentIdCloud", currentIdCloud)
                        Log.i("listId", listIds.toString())
                        // Cek apakah id cloud tidak sama dgn local
                        if(!listIds.contains(currentIdCloud)){
                            val trx = Transaction(
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
                            Snackbar.make(
                                view,
                                "Transaksi lokal sudah terbaru.",
                                Snackbar.LENGTH_SHORT
                            )
                                .setAction("OK") { }.show()
                        }
                    }
                }else{
                    // Snackbar notification
                    Snackbar.make(view, "Tidak ada data di internet.", Snackbar.LENGTH_SHORT)
                        .setAction("OK") { }.show()
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    fun checkCameraPermission(): Boolean? {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val extras = data!!.extras
            val imageBitmap = extras!!["data"] as Bitmap?

            if (imageBitmap != null) {
                uploadImageToImgur(imageBitmap)
            }
        }
    }

    private fun getBase64Image(image: Bitmap, complete: (String) -> Unit) {
        GlobalScope.launch {
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val b = outputStream.toByteArray()
            complete(Base64.encodeToString(b, Base64.DEFAULT))
        }
    }

    private val CLIENT_ID = "a199127d5918115"

    private fun uploadImageToImgur(image: Bitmap) {
        getBase64Image(image, complete = { base64Image ->
            GlobalScope.launch(Dispatchers.Default) {
                val url = URL("https://api.imgur.com/3/image")

                val boundary = "Boundary-${System.currentTimeMillis()}"

                val httpsURLConnection =
                    withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                httpsURLConnection.setRequestProperty("Authorization", "Client-ID $CLIENT_ID")
                httpsURLConnection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )

                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                var body = ""
                body += "--$boundary\r\n"
                body += "Content-Disposition:form-data; name=\"image\""
                body += "\r\n\r\n$base64Image\r\n"
                body += "--$boundary--\r\n"


                val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
                withContext(Dispatchers.IO) {
                    outputStreamWriter.write(body)
                    outputStreamWriter.flush()
                }


                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                val jsonObject = JSONTokener(response).nextValue() as JSONObject
                val data = jsonObject.getJSONObject("data")

                val linkUploaded = data.getString("link")
                Log.d("TAG", "Link is : ${linkUploaded}")

                val database = FirebaseDatabase.getInstance().reference
                val id = FirebaseAuth.getInstance().currentUser.uid
                // Simpan ke firebase
                database.child("gambarProfile").child(id).setValue(linkUploaded).addOnSuccessListener {
                    val options: RequestOptions = RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.empty)
                        .error(R.drawable.empty)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                    Glide.with(requireView()).load(linkUploaded).apply(options).into(gambarUserImage)
                    // Snackbar notification
                    Snackbar.make(requireView(), "Sukses menyimpan gambar", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                }.addOnFailureListener{
                    // Snackbar notification
                    Snackbar.make(requireView(), "Gagal menyimpan gambar ke internet", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show()
                }

                Snackbar.make(requireView(), "Sukses menyimpan gambar", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            }
        })
    }

}
