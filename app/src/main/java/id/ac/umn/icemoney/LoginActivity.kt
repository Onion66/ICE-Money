package id.ac.umn.icemoney

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.passwordText
import kotlinx.android.synthetic.main.activity_login.usernameText
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        createNotificationChannel()

        loginButton.setOnClickListener{
            val email = usernameText.text.toString().trim()
            val password = passwordText.text.toString().trim()

            if(email.isEmpty()){
                usernameText.error = "Email harus diisi"
                usernameText.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                usernameText.error = "Email tidak valid"
                usernameText.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty() || password.length < 6){
                passwordText.error = "Password minimal 6 karakter"
                passwordText.requestFocus()
                return@setOnClickListener
            }

            loginUser(email, password)

        }

        tvRegister.setOnClickListener{
            Intent(this@LoginActivity, RegisterActivity::class.java).also{
                startActivity(it)
            }
        }
    }

    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    sendNotification()
                    Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginActivity, HomeActivity::class.java).also { intent->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
                else{
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            Intent(this@LoginActivity, HomeActivity::class.java).also { intent->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    //notification
    private fun createNotificationChannel(){
        //if statement ini digunakan untuk versi android terbaru (26 keatas)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT //bisa diubah ke Hgh / Low tergantung maunya apa
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ice)

        //fungsi ini untuk older version dari android
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_ice_money_square)
            .setContentTitle("Logged In")
            .setContentText("Welcome to ICE-Money")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

}