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

        image_1.setImageBitmap(getBitmapFromAssets("foto_jojo.jpg"))
        desc_1_nama.text = "Jonathan"
        desc_1_email.text = "jonathan8@student.umn.ac.id"
        desc_1_nim.text = "00000030182"

        image_2.setImageBitmap(getBitmapFromAssets("foto_darren.jpg"))
        desc_2_nama.text = "Alfonso Darren Vincentio"
        desc_2_email.text = "alfonso.vincentio@student.umn.ac.id"
        desc_2_nim.text = "00000029324"

        image_3.setImageBitmap(getBitmapFromAssets("foto_chendra.jpg"))
        desc_3_nama.text = "Chendra Dewangga"
        desc_3_email.text = "chendra.dewangga@student.umn.ac.id"
        desc_3_nim.text = "00000029610"

        image_4.setImageBitmap(getBitmapFromAssets("foto_daniel.jpg"))
        desc_4_nama.text = "Daniel Wijaya"
        desc_4_email.text = "daniel.wijaya2@student.umn.ac.id"
        desc_4_nim.text = "00000029854"
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