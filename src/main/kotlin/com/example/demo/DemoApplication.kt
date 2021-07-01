package com.example.demo

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.category.CategoryDataset
import org.jfree.data.general.DatasetUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Bean
import java.io.File

@SpringBootApplication
@ServletComponentScan
class DemoApplication {

    @Bean
    fun commandLineRunner(): CommandLineRunner {
        return CommandLineRunner {
            val dataset: CategoryDataset = DatasetUtils.createCategoryDataset(
                "rowkey", "columnkey", arrayOf(
                    doubleArrayOf(2.0, 3.0), doubleArrayOf(2.0, 3.0)
                )
            )

            val pieData = DatasetUtils.createPieDatasetForRow(dataset, 0)
            val chart: JFreeChart = ChartFactory.createPieChart("title", pieData, true, false, true)
            val file: File = File("/Users/zw/Pictures/SVGDemo.png")
            ChartUtils.writeChartAsPNG(file.outputStream(), chart, 400, 400)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}


