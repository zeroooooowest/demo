package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloWorldJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun job(): Job = jobBuilderFactory.get("basicJob")
        .start(step1())
        .build()

    @Bean
    fun step1(): Step = stepBuilderFactory.get("step1")
        .tasklet(helloWorldTasklet())
        .build()

    @Bean
    fun helloWorldTasklet(): Tasklet = Tasklet { contribution, chunkContext ->
        val name = chunkContext.stepContext.jobParameters
            .getOrDefault("name", "zerowest")
                as String

        logger.info("Hello, ${name}!")
        RepeatStatus.FINISHED
    }

    @Bean
    fun validator(): CompositeJobParametersValidator{
        val validator = CompositeJobParametersValidator()

        val defaultJobParametersValidator = DefaultJobParametersValidator(
            arrayOf("fileName"), arrayOf("name", "currentDate")
        )

        defaultJobParametersValidator.afterPropertiesSet()
        validator.setValidators(
            listOf()
        )
    }
}