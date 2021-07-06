package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExecutionContextConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {
    companion object : Log

    @Bean
    fun ecJob(): Job = jobBuilderFactory.get("ecJob")
        .start(ecStep1())
        .build()

    @Bean
    @JobScope
    fun ecStep1(): Step = stepBuilderFactory.get("ecStep1")
        .tasklet(helloWorldTasklet())
        .build()

    @Bean
    fun helloWorldTasklet(): Tasklet = Tasklet { contribution, chunkContext ->
        val name = chunkContext.stepContext
            .jobParameters
            .get("name") as String

        val jobContext = chunkContext.stepContext
            .stepExecution
            .executionContext
        jobContext.put("name", name)
        logger.info("Hello, $name")

        RepeatStatus.FINISHED
    }

}