package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class StepNextJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {
    companion object : Log

    @Bean
    fun stepNextJob(): Job {
        return jobBuilderFactory.get("stepNextJob")
            .start(step1())
            .next(step2())
            .next(step3())
            .build()
    }

    @Bean
    @JobScope
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is step1")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    @JobScope
    fun step2(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is step2")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    @JobScope
    fun step3(): Step {
        return stepBuilderFactory.get("step3")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is step3")
                RepeatStatus.FINISHED
            }.build()
    }
}