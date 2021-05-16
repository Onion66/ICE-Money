package id.ac.umn.icemoney.view.transaction

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import id.ac.umn.icemoney.R
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.fragment_add_income.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddIncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddIncomeFragment : Fragment() {
    lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCallBack()
        // Event Listener Here
        initUICallBack()
    }

    private fun initCallBack() {
        // Income
        tvInputAddIncomeAmount.setOnClickListener {
//            tvBottomSheetLabelInput.text = "Amount"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddIncomeCategory.setOnClickListener {
//            tvBottomSheetLabelInput.text = "Category"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddIncomeDate.setOnClickListener {
//            tvBottomSheetLabelInput.text = "Date"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvInputAddIncomePayment.setOnClickListener {
//            tvBottomSheetLabelInput.text = "Payment"
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_income, container, false)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is TransactionListener) {
//            listener = context
//        } else {
//            throw ClassCastException("$context must implement TransactionListener")
//        }
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddIncomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: String, param2: String*/ bottomSheetBehavior: BottomSheetBehavior<FrameLayout>) =
            AddIncomeFragment().apply {
                this.bottomSheetBehavior = bottomSheetBehavior
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
        var listener : TransactionListener? = null
    }

    private fun initUICallBack() {
        saveButton.setOnClickListener { view ->
            // TODO: Cek nilai dari tiap field

            // TODO: Simpan ke DAO

            // Snackbar berhasil ditambahkan
            Snackbar.make(view, "Berhasil ditambahkan", Snackbar.LENGTH_LONG)
                .setAction("Kembali ke Home", View.OnClickListener {
                    activity?.finish()
                }).show()

        }

//        btnBottomSheetClose.setOnClickListener {
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        }
    }
}