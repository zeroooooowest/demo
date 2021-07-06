package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.ExitStatus
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
class StepNextConditionalJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun stepNextConditionalJob(): Job {
        return jobBuilderFactory.get("stepNextConditionalJob")
            .start(conditionalJobStep1())
            .on("FAILED")
            .to(conditionalJobStep3())
            .on("*")
            .end()
            .from(conditionalJobStep1())
            .on("*")
            .to(conditionalJobStep2())
            .next(conditionalJobStep3())
            .on("*")
            .end()
            .end()
            .build()
    }

    @Bean
    @JobScope
    fun conditionalJobStep1(): Step {
        return stepBuilderFactory.get("conditionalJobStep1")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is stepNextConditionalJob Step1")
                contribution.exitStatus = ExitStatus.FAILED
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    @JobScope
    fun conditionalJobStep2(): Step {
        return stepBuilderFactory.get("conditionalJobStep2")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is stepNextConditionalJob Step2")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    @JobScope
    fun conditionalJobStep3(): Step {
        return stepBuilderFactory.get("conditionalJobStep3")
            .tasklet { contribution, chunkContext ->
                logger.info(">>>>> This is stepNextConditionalJob Step3")
                RepeatStatus.FINISHED
            }.build()
    }

}