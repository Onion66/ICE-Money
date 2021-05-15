package id.ac.umn.icemoney.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import id.ac.umn.icemoney.R


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

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

        val aaChartView = root.findViewById<AAChartView>(R.id.aa_chart_view)
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("Pengeluaran Bulan Ini")
            .titleStyle(AAStyle().color("#00000"))
            .subtitle("1 Mei 2021 - 15 Mei 2021 (Now)")
            .subtitleStyle(AAStyle().color("#00000"))
//            .backgroundColor("#86B5FC")
            .dataLabelsEnabled(true)
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Pengeluaran")
                        .data(arrayOf(
                            arrayOf<Any>("Makanan", 300000),
                            arrayOf<Any>("Minuman", 100000),
                            arrayOf<Any>("Transportasi", 50000),
                            arrayOf<Any>("Pacaran", 325000)
                        )),
                )
            )

        aaChartView.aa_drawChartWithChartModel(aaChartModel)

        return root
    }
}