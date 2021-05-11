package id.ac.umn.icemoney.ui.setting

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
import com.google.firebase.auth.FirebaseAuth
import id.ac.umn.icemoney.LoginActivity
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.RegisterActivity
import kotlinx.android.synthetic.main.fragment_setting.*
import java.lang.Exception

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        val textView: TextView = root.findViewById(R.id.text_setting)
        settingViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

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
