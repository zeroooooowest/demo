package com.example.demo

import com.example.demo.slack.SlackService
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.category.CategoryDataset
import org.jfree.data.general.DatasetUtils
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.io.File

@EnableBatchProcessing
@SpringBootApplication
class DemoApplication {

//    @Bean
    fun commandLineRunner(
        @Autowired slackService: SlackService,
    ): CommandLineRunner {
        return CommandLineRunner {
            val dataset: CategoryDataset = DatasetUtils.createCategoryDataset(
                "rowkey", "columnkey", arrayOf(
                    doubleArrayOf(2.0, 3.0), doubleArrayOf(2.0, 3.0)
                )
            )

            val imagePath = "/Users/zw/Pictures/ChartDemo.png"

            val pieData = DatasetUtils.createPieDatasetForRow(dataset, 0)
            val chart: JFreeChart = ChartFactory.createPieChart("title", pieData, true, false, true)
            val file: File = File(imagePath)
            println("ewqijoewjqo")
            ChartUtils.writeChartAsPNG(file.outputStream(), chart, 400, 400)
            println("eqwijojoeqwjoi")
            slackService.sendImage("Hello Chart!", imagePath)
            println("ewojqoj")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}


