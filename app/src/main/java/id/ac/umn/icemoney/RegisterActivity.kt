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

                registerButton.setOnClickListener{
                        val email = emailText.text.toString().trim()
                        val password = passwordText.text.toString().trim()
                        val confirmpassword = confirmPasswordText.text.toString().trim()

                        if(email.isEmpty()){
                                emailText.error = "Email harus diisi"
                                emailText.requestFocus()
                                return@setOnClickListener
                        }

                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                emailText.error = "Email tidak valid"
                                emailText.requestFocus()
                                return@setOnClickListener
                        }

                        if(email.isEmpty() || password.length < 6){
                                passwordText.error = "Password minimal 6 karakter"
                                passwordText.requestFocus()
                                return@setOnClickListener
                        }

                        if(confirmpassword != password){
                                confirmPasswordText.error = "Password tidak sama dengan confirm password"
                                confirmPasswordText.requestFocus()
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