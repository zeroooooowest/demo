package com.example.demo.batch.rest

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RestBatchConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun restJob(): Job = jobBuilderFactory.get("restJob")
        .incrementer(RunIdIncrementer())
        .start(restStep1())
        .build()

    @Bean
    fun restStep1(): Step = stepBuilderFactory.get("restStep1")
        .tasklet { contribution, chunkContext ->
            logger.info("step 1 ran today!")
            RepeatStatus.FINISHED
        }.build()

}