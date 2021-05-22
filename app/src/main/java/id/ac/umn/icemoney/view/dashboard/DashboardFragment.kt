package id.ac.umn.icemoney.view.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import id.ac.umn.icemoney.R
import id.ac.umn.icemoney.model.TransactionChart
import id.ac.umn.icemoney.utils.TransactionUtils
import id.ac.umn.icemoney.view.home.TransactionViewModel
import java.time.LocalDate


class DashboardFragment : Fragment() {
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var expenseList: List<TransactionChart>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseList = emptyList()

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        val currentTime = LocalDate.now()
        val currentMonth = currentTime.toString().take(7).takeLast(2)

        val aaChartView = view.findViewById<AAChartView>(R.id.aa_chart_view)

        transactionViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            expenseList = TransactionUtils.getExpenseByMonth(it, currentMonth)
            val toChart = mutableListOf<List<Any>>()
            val listKategori = TransactionUtils.listKategori
            val listKategoriMap = mutableListOf(
                0,0,0,0,0,0,0
            )

            expenseList.forEach {
                when(it.name) {
                    listKategori[0] -> listKategoriMap[0] += it.amount.toInt()
                    listKategori[1] -> listKategoriMap[1] += it.amount.toInt()
                    listKategori[2] -> listKategoriMap[2] += it.amount.toInt()
                    listKategori[3] -> listKategoriMap[3] += it.amount.toInt()
                    listKategori[4] -> listKategoriMap[4] += it.amount.toInt()
                    listKategori[5] -> listKategoriMap[5] += it.amount.toInt()
                    listKategori[6] -> listKategoriMap[6] += it.amount.toInt()
                }
//                toChart.add(listOf(it.name,it.amount))
            }

            listKategori.forEachIndexed {idx, element ->
                if(listKategoriMap[idx] != 0){
                    toChart.add(listOf(listKategori[idx],listKategoriMap[idx]))
                }
            }
            Log.i("listKategoriMap","${listKategoriMap.toList()}")
            Log.i("toChart","${toChart.toList()}")

            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Pie)
                .title("Pengeluaran Bulan Ini")
                .titleStyle(AAStyle().color("#00000"))
                .subtitle(currentTime.toString().take(7))
                .subtitleStyle(AAStyle().color("#00000"))
//            .backgroundColor("#86B5FC")
                .dataLabelsEnabled(true)
                .series(
                    arrayOf(
                        AASeriesElement()
                            .name("Pengeluaran")
                            .data(toChart.toTypedArray()),
                    )
                )

            aaChartView.aa_drawChartWithChartModel(aaChartModel)
        })
    }
}