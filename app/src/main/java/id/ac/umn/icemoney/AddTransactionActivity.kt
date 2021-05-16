package id.ac.umn.icemoney

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.ac.umn.icemoney.view.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.activity_add_transaction.inputBottomSheet

class AddTransactionActivity : AppCompatActivity() {
    lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        initBottomSheet()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, bottomSheetBehavior)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
//        val fab: FloatingActionButton = findViewById(R.id.fab)

        // Event Listeners Here
        initUICallBack()
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(inputBottomSheet).apply {
            peekHeight = 0
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> fabSaveTransaction.visibility = View.VISIBLE
//                    BottomSheetBehavior.STATE_EXPANDED -> fabSaveTransaction.visibility = View.GONE
//                    BottomSheetBehavior.STATE_DRAGGING -> fabSaveTransaction.visibility = View.VISIBLE
//                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(this@AddTransactionActivity, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
//                    BottomSheetBehavior.STATE_HIDDEN -> fabSaveTransaction.visibility = View.VISIBLE
//                    else -> Toast.makeText(this@AddTransactionActivity, "OTHER_STATE", Toast.LENGTH_SHORT).show()
//                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                fabSaveTransaction.visibility = View.VISIBLE
                fabSaveTransaction.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).withEndAction {
                    if (slideOffset == 1.0F) fabSaveTransaction.visibility = View.GONE else if (slideOffset == 0.0F) fabSaveTransaction.visibility = View.VISIBLE
                }.withStartAction {
                    if (slideOffset == 0.0F) fabSaveTransaction.visibility = View.VISIBLE else if (slideOffset == 1.0F) fabSaveTransaction.visibility = View.GONE
                }.start()
            }
        })
    }

    private fun initUICallBack() {
        fabSaveTransaction.setOnClickListener { view ->
            Snackbar.make(view, "Successfully Added", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            finish()
        }

        btnBottomSheetClose.setOnClickListener {
            fabSaveTransaction.visibility = View.VISIBLE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}