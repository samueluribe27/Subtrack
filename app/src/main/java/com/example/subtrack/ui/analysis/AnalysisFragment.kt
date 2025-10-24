package com.example.subtrack.ui.analysis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.example.subtrack.R

class AnalysisFragment : Fragment() {

    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_analysis, container, false)

        pieChart = view.findViewById(R.id.pieChart)
        setupPieChart()

        // Ejemplo de datos dinámicos (esto luego puedes reemplazarlo por tus datos reales)
        val categorias = mapOf(
            "Entretenimiento" to 22.98f,
            "Almacenamiento" to 10.98f,
            "Educación" to 5.99f,
            "Productividad" to 12.50f
        )

        loadPieChartData(categorias)

        return view
    }

    private fun setupPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.isRotationEnabled = true
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setHoleRadius(50f)
        pieChart.transparentCircleRadius = 55f
        pieChart.centerText = "Gastos"
        pieChart.setCenterTextSize(14f)
        pieChart.animateY(1400)

        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.isWordWrapEnabled = true
        legend.textSize = 12f
    }

    private fun loadPieChartData(categorias: Map<String, Float>) {
        val entries = mutableListOf<PieEntry>()
        categorias.forEach { (categoria, valor) ->
            entries.add(PieEntry(valor, categoria))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextSize = 14f
        dataSet.sliceSpace = 2f

        val data = PieData(dataSet)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data
        pieChart.invalidate() // refresca
    }
}
