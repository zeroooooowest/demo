package com.example.demo.chart

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.category.CategoryDataset
import org.jfree.data.general.DatasetUtils
import org.springframework.stereotype.Service
import java.io.File

@Service
class ChartService {

    fun makePieChart(dataset: CategoryDataset){
        val dataset: CategoryDataset = DatasetUtils.createCategoryDataset(
            "rowkey", "columnkey", arrayOf(
                doubleArrayOf(2.0, 3.0), doubleArrayOf(2.0, 3.0)
            )
        )

        val imagePath = "/Users/zw/Pictures/ChartDemo.png"

        val pieData = DatasetUtils.createPieDatasetForRow(dataset, 0)
        val chart: JFreeChart = ChartFactory.createPieChart("title", pieData, true, false, true)
        val file: File = File(imagePath)
        ChartUtils.writeChartAsPNG(file.outputStream(), chart, 400, 400)
    }

}