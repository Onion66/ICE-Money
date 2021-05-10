package id.ac.umn.icemoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.passwordText
import kotlinx.android.synthetic.main.activity_login.submitButton
import kotlinx.android.synthetic.main.activity_login.tvRegister
import kotlinx.android.synthetic.main.activity_login.usernameText
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

            if(password.isEmpty() || password.length < 6){
                passwordText.error = "Password harus lebih dari 6 karater"
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
//    override fun onStart() {
//        super.onStart()
//        if(auth.currentUser != null){
//            Intent(this@LoginActivity, HomeActivity::class.java).also { intent->
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(intent)
//            }
//        }
//    }

}