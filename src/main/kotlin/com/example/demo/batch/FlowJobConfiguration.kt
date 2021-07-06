package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlowJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun loadStockFile(): Tasklet = Tasklet { contribution, chunkContext ->
        logger.info("The stock file has been loaded")
        RepeatStatus.FINISHED
    }

    @Bean
    fun loadCustomerFile(): Tasklet = Tasklet { contribution, chunkContext ->
        logger.info("The customer file has been loaded")
        RepeatStatus.FINISHED
    }

    @Bean
    fun updateStart(): Tasklet = Tasklet { contribution, chunkContext ->
        logger.info("The start has been updated")
        RepeatStatus.FINISHED
    }

    @Bean
    fun runBatchTasklet(): Tasklet = Tasklet { contribution, chunkContext ->
        logger.info("The batch has been run")
        RepeatStatus.FINISHED
    }

    @Bean
    fun preProcessingFlow(): Flow = FlowBuilder<Flow>("preProcessingFlow")
        .start(loadFileStep())
        .next(loadCustomerStep())
        .next(updateStartStep())
        .build()

    @Bean
    fun conditionalStepLogicJob(): Job = jobBuilderFactory.get("conditionalStepLogicJob")
        .start(preProcessingFlow())
        .next(runBatch())
        .end()
        .build()

    @Bean
    fun loadFileStep(): Step = stepBuilderFactory.get("loadFileStep")
        .tasklet(loadStockFile())
        .build()

    @Bean
    fun loadCustomerStep(): Step = stepBuilderFactory.get("loadCustomerStep")
        .tasklet(loadCustomerFile())
        .build()

    @Bean
    fun updateStartStep(): Step = stepBuilderFactory.get("updateStartStep")
        .tasklet(updateStart())
        .build()

    @Bean
    fun runBatch(): Step = stepBuilderFactory.get("runBatch")
        .tasklet(runBatchTasklet())
        .build()

}