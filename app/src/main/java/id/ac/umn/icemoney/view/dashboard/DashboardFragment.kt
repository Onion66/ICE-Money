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

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var expenseList: List<TransactionChart>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

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
            var toChart = mutableListOf<List<Any>>()
            expenseList.forEach {
                toChart.add(listOf(it.name, it.amount))
            }

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