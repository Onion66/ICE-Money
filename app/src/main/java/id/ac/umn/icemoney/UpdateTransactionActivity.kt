package id.ac.umn.icemoney

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.ac.umn.icemoney.entity.Transaction
import kotlinx.android.synthetic.main.activity_add_transaction.*

class UpdateTransactionActivity : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        val id : String

        if (intent.hasExtra("data")) {
            val data = intent.getSerializableExtra("data") as Transaction
            tvInputAddTransactionName.setText(data.name)
            tvInputAddTransactionAmount.text = data.amount.toString()
            tvInputAddTransactionType.text = if (data.isIncome) "Pemasukan" else "Pengeluaran"
            tvInputAddTransactionCategory.text = data.category
            tvInputAddTransactionPayment.text = data.paymentMethod
            tvInputAddTransactionDate.text = data.date
            id = data.id
        } else {
            id = "X"
        }

        fabSaveTransaction.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("data", Transaction(
                id,
                tvInputAddTransactionAmount.text.toString().toLong(),
                tvInputAddTransactionCategory.text.toString(),
                tvInputAddTransactionDate.text.toString(),
                tvInputAddTransactionType.text.equals("Pemasukan"),
                tvInputAddTransactionName.text.toString(),
                tvInputAddTransactionPayment.text.toString()
            )))
            finish()
        }

        bottomSheetBehavior = BottomSheetBehavior.from(inputBottomSheet).apply {
            peekHeight = 0
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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
                fabSaveTransaction.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset)
                    .setDuration(
                        0
                    ).withEndAction {
                        if (slideOffset == 1.0F) {
                            fabSaveTransaction.visibility = View.GONE
                        } else if (slideOffset == 0.0F) {
                            fabSaveTransaction.visibility = View.VISIBLE
                            dpInputDate.visibility = View.GONE
                            scInputType.visibility = View.GONE
                            llInputAmount.visibility = View.GONE
                            scInputPayment.visibility = View.GONE
                            scInputCategory.visibility = View.GONE
                            tvInputAddTransactionType.isEnabled = true
                            tvInputAddTransactionPayment.isEnabled = true
                            tvInputAddTransactionCategory.isEnabled = true
                            tvInputAddTransactionAmount.isEnabled = true
                            tvInputAddTransactionDate.isEnabled = true
                        }
                    }.withStartAction {
                        if (slideOffset == 0.0F) {
                            fabSaveTransaction.visibility = View.VISIBLE
                            dpInputDate.visibility = View.GONE
                            scInputType.visibility = View.GONE
                            llInputAmount.visibility = View.GONE
                            scInputPayment.visibility = View.GONE
                            scInputCategory.visibility = View.GONE
                            tvInputAddTransactionType.isEnabled = true
                            tvInputAddTransactionPayment.isEnabled = true
                            tvInputAddTransactionCategory.isEnabled = true
                            tvInputAddTransactionAmount.isEnabled = true
                            tvInputAddTransactionDate.isEnabled = true
                        } else if (slideOffset == 1.0F) {
                            fabSaveTransaction.visibility = View.GONE
                        }
                    }.start()
            }
        })

        tvInputAddTransactionAmount.setOnClickListener {
            llInputAmount.visibility = View.VISIBLE
            tvInputAddTransactionType.isEnabled = false
            tvInputAddTransactionPayment.isEnabled = false
            tvInputAddTransactionCategory.isEnabled = false
            tvInputAddTransactionDate.isEnabled = false
            tvBottomSheetLabelInput.text = "Jumlah"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddTransactionCategory.setOnClickListener {
            scInputCategory.addItems(
                listOf(
                    "Makanan",
                    "Minuman",
                    "Transportasi",
                    "Kesehatan",
                    "Pendidikan",
                    "Hiburan",
                    "Lain-lain"
                ), 0
            )
            scInputCategory.visibility = View.VISIBLE
            tvInputAddTransactionType.isEnabled = false
            tvInputAddTransactionPayment.isEnabled = false
            tvInputAddTransactionAmount.isEnabled = false
            tvInputAddTransactionDate.isEnabled = false
            tvBottomSheetLabelInput.text = "Category"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddTransactionDate.setOnClickListener {
            dpInputDate.visibility = View.VISIBLE
            tvInputAddTransactionType.isEnabled = false
            tvInputAddTransactionPayment.isEnabled = false
            tvInputAddTransactionAmount.isEnabled = false
            tvInputAddTransactionCategory.isEnabled = false
            tvBottomSheetLabelInput.text = "Tanggal"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddTransactionPayment.setOnClickListener {
            scInputPayment.addItems(listOf("Cash", "OVO", "GoPay", "Dana","Kartu Kredit"), 0)
            scInputPayment.visibility = View.VISIBLE
            tvInputAddTransactionType.isEnabled = false
            tvInputAddTransactionCategory.isEnabled = false
            tvInputAddTransactionAmount.isEnabled = false
            tvInputAddTransactionDate.isEnabled = false
            tvBottomSheetLabelInput.text = "Pembayaran"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddTransactionType.setOnClickListener {
            scInputType.addItems(listOf("Pengeluaran", "Pemasukan"), 0)
            scInputType.visibility = View.VISIBLE
            tvInputAddTransactionPayment.isEnabled = false
            tvInputAddTransactionCategory.isEnabled = false
            tvInputAddTransactionAmount.isEnabled = false
            tvInputAddTransactionDate.isEnabled = false
            tvBottomSheetLabelInput.text = "Type"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btnBottomSheetClose.setOnClickListener {
            fabSaveTransaction.visibility = View.VISIBLE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        btnInputExit.setOnClickListener {
            fabSaveTransaction.visibility = View.VISIBLE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        dpInputDate.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            var day:String = dayOfMonth.toString()
            if(day.length == 1){
                day = "0" + day
            }
            var tempMonth = monthOfYear + 1
            var month:String = tempMonth.toString()
            if(month.length == 1){
                month = "0" + month
            }
            tvInputAddTransactionDate.text = "${day}-${month}-${year}"
        }

        scInputType.setOnItemSelectedListener { scrollChoice, position, name ->
            tvInputAddTransactionType.text = name.toString()
        }

        scInputPayment.setOnItemSelectedListener { scrollChoice, position, name ->
            tvInputAddTransactionPayment.text = name.toString()
        }

        scInputCategory.setOnItemSelectedListener { scrollChoice, position, name ->
            tvInputAddTransactionCategory.text = name.toString()
        }

        // Keyboard
        btnInputOne.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}1"
        }

        btnInputTwo.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}2"
        }

        btnInputThree.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}3"
        }

        btnInputFour.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}4"
        }

        btnInputFive.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}5"
        }

        btnInputSix.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}6"
        }

        btnInputSeven.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}7"
        }

        btnInputEight.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}8"
        }

        btnInputNine.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}9"
        }

        btnInputZero.setOnClickListener {
            tvInputAddTransactionAmount.text = "${tvInputAddTransactionAmount.text}0"
        }

        btnInputBackSpace.setOnClickListener {
            tvInputAddTransactionAmount.text = tvInputAddTransactionAmount.text.replaceFirst(
                ".$".toRegex(),
                ""
            )
        }
    }
}