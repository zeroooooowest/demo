package com.example.demo.batch.explore

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobExplorerBatchConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
    @Autowired private val jobExplorer: JobExplorer
) {

    @Bean
    fun explorerTasklet(): Tasklet = ExploringTasklet(jobExplorer)

    @Bean
    fun explorerStep(): Step = stepBuilderFactory.get("explorerStep")
        .tasklet(explorerTasklet())
        .build()

    @Bean
    fun explorerJob(): Job = jobBuilderFactory.get("exploreJob")
        .start(explorerStep())
        .build()

}