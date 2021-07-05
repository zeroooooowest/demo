package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Configuration
class ScopeJobConfig(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun scopeJob(): Job = jobBuilderFactory.get("scopeJob")
        .start(scopeStep1(null))
        .next(scopeStep2())
        .build()

    @Bean
    @JobScope
    fun scopeStep1(
        @Value("#{jobParameters[requestDate] != null ? jobParameters[requestDate] : 20200101}")
        requestDate: String?
    ): Step =
        stepBuilderFactory.get("scopeStep1")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is scopeStep1")
                logger.info(">>>>> requestDate = ${LocalDate.parse(requestDate, DateTimeFormatter.BASIC_ISO_DATE)}")
                RepeatStatus.FINISHED
            }.build()

    @Bean
    fun scopeStep2(): Step = stepBuilderFactory.get("scopeStep2")
        .tasklet(scopeStep2Tasklet(null))
        .build()

    @Bean
    @StepScope
    fun scopeStep2Tasklet(
        @Value("#{jobParameters[requestDate] != null ? jobParameters[requestDate] : 20200101}")
        requestDate: String?
    ): Tasklet =
        Tasklet { contribution, chunkContext ->
            logger.info(">>>>> This is scopeStep2")
            logger.info(">>>>> requestDate = ${LocalDate.parse(requestDate, DateTimeFormatter.BASIC_ISO_DATE)}")
            RepeatStatus.FINISHED
        }
}