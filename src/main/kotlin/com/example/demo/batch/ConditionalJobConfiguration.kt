package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.RuntimeException

@Configuration
class ConditionalJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {
    companion object : Log

    @Bean
    fun passTasklet(): Tasklet = Tasklet { contribution, chunkContext ->
        RepeatStatus.FINISHED
//        throw RuntimeException("This is a faiilure")
    }

    @Bean
    fun successTasklet(): Tasklet = Tasklet { contribution, chunkContext ->
        logger.info("Success!")
        RepeatStatus.FINISHED
    }

    @Bean
    fun failTasklet(): Tasklet = Tasklet { contribution, chunkContext ->
        logger.info("Failure!")
        RepeatStatus.FINISHED
    }

    @Bean
    fun conditionalJob(): Job = jobBuilderFactory.get("conditionalJob")
        .start(conditionalStep1())
        .on("FAILED").to(failureStep())
        .from(conditionalStep1()).on("*").to(successStep())
        .end()
        .build()

    @Bean
    fun conditionalStep1(): Step = stepBuilderFactory.get("conditionalStep1")
        .tasklet(passTasklet())
        .build()

    @Bean
    fun successStep(): Step = stepBuilderFactory.get("successStep")
        .tasklet(successTasklet())
        .build()

    @Bean
    fun failureStep(): Step = stepBuilderFactory.get("failureStep")
        .tasklet(failTasklet())
        .build()

}