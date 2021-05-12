package id.ac.umn.icemoney

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_about_us.*
import java.io.IOException
import java.io.InputStream

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        image_daniel.setImageBitmap(getBitmapFromAssets("foto_daniel.jpg"))
        desc_daniel.text = "Daniel Wijaya\nEhehehehehe"

        image_jojo.setImageBitmap(getBitmapFromAssets("foto_daniel.jpg"))
        desc_jojo.text = "Jojo ehehe\nEhehehehehe"

        image_darren.setImageBitmap(getBitmapFromAssets("foto_daniel.jpg"))
        desc_darren.text = "Darren ehe\nEhehehehehe"

        image_chendra.setImageBitmap(getBitmapFromAssets("foto_daniel.jpg"))
        desc_chendra.text = "Chendra :)\nEhehehehehe"
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