package id.ac.umn.icemoney

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.io.IOException
import java.io.InputStream

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splash_image.setImageBitmap(getBitmapFromAssets("splash_image.png"))

        // Move to LoginActivity after 1 seconds
        Handler().postDelayed({
            Intent(this, LoginActivity::class.java).also{
                startActivity(it)
                finish()
            }
        }, 1000)
    }
    private fun getBitmapFromAssets(fileName: String): Bitmap? {
        val am = assets
        var `is`: InputStream? = null
        try {
            `is` = am.open(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return BitmapFactory.decodeStream(`is`)
    }
}