package id.ac.umn.icemoney

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val stringValue = "Don't Have an account? <b> Register </b> "
        tvRegister.setText(HtmlCompat.fromHtml(stringValue, HtmlCompat.FROM_HTML_MODE_LEGACY))
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