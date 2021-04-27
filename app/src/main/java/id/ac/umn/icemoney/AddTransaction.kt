package id.ac.umn.icemoney

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import id.ac.umn.icemoney.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_add_transaction.*

class AddTransaction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = findViewById(R.id.fab)

        // Event Listeners Here
        initUICallBack()
    }

    fun initUICallBack() {
        fabSaveTransaction.setOnClickListener { view ->
            Snackbar.make(view, "Successfully Added", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            finish()
        }
    }
}