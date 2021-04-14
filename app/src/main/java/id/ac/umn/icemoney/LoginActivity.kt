package id.ac.umn.icemoney

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "Login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val stringValue = "Don't Have an account? <b> Register </b> "
        tvRegister.setText(HtmlCompat.fromHtml(stringValue, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun reload() {

    }


    private fun updateUI(user: FirebaseUser?) {

    }
    public fun loginClick(view: View) {
        // get value of username & password
        val username = username.editText?.text.toString()
        val password = password.editText?.text.toString()

        // checking the username and password
        if(username == "uasmobile" && password == "uasmobilegenap"){
//            val submitIntent = Intent(this, ListLaguActivity::class.java)
//            submitIntent.putExtra("position","login")
//            startActivity(submitIntent)
        }else{
            Toast.makeText(this, "Username and/or password is incorrect!", Toast.LENGTH_LONG).show()
        }
    }

}