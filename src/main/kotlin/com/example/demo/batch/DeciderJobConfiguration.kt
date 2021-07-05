package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.flow.FlowExecutionStatus
import org.springframework.batch.core.job.flow.JobExecutionDecider
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class DeciderJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object OddDecider : JobExecutionDecider, Log {

        override fun decide(jobExecution: JobExecution, stepExecution: StepExecution?): FlowExecutionStatus {
            val rand = Random()
            val randNumber = rand.nextInt(50) + 1
            logger.info("랜덤 숫자: $randNumber")

            return if (randNumber % 2 == 0) FlowExecutionStatus("EVEN") else FlowExecutionStatus("ODD")
        }
    }

    @Bean
    fun deciderJob(): Job = jobBuilderFactory.get("deciderJob")
        .start(startStep())
        .next(decider())
        .from(decider())
        .on("ODD")
        .to(oddStep())
        .from(decider())
        .on("EVEN")
        .to(evenStep())
        .end()
        .build()

    @Bean
    fun startStep(): Step = stepBuilderFactory.get("startStep")
        .tasklet { contribution, chunkContext ->
            logger.info(">>>>> Start!")
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun evenStep(): Step = stepBuilderFactory.get("evenStep")
        .tasklet { contribution, chunkContext ->
            logger.info(">>>>> 짝수입니다.")
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun oddStep(): Step = stepBuilderFactory.get("oddStep")
        .tasklet { contribution, chunkContext ->
            logger.info(">>>>> 홀수입니다.")
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun decider(): JobExecutionDecider = OddDecider


}