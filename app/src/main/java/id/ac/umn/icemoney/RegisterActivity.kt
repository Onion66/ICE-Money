package id.ac.umn.icemoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

        private lateinit var auth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_register)

                auth = FirebaseAuth.getInstance()

                submitButton.setOnClickListener{
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

                        if(email.isEmpty() || password.length < 6){
                                passwordText.error = "Password harus lebih dari 6 karater"
                                passwordText.requestFocus()
                                return@setOnClickListener
                        }

                        registerUser(email, password)
                }

                tvLogin.setOnClickListener{
                        Intent(this@RegisterActivity, LoginActivity::class.java).also{
                                startActivity(it)
                        }
                }
        }

        private fun registerUser(email: String, password: String){
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this){
                                if(it.isSuccessful){
                                        Toast.makeText(this, "Berhasil Register", Toast.LENGTH_SHORT).show()
                                        Intent(this@RegisterActivity, HomeActivity::class.java).also { intent->
                                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                startActivity(intent)
                                        }
                                }else{
                                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
        }

//    override fun onStart() {
//        super.onStart()
//        if(auth.currentUser != null){
//            Intent(this@RegisterActivity, HomeActivity::class.java).also { intent->
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//            }
//        }
//    }
}