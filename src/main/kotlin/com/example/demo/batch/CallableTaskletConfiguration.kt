package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Callable

@Configuration
class CallableTaskletConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun callableJob(): Job = jobBuilderFactory.get("callableJob")
        .start(callableStep())
        .build()

    @Bean
    fun callableStep(): Step = stepBuilderFactory.get("callableStep")
        .tasklet(callableTasklet())
        .build()

    @Bean
    fun callableObject(): Callable<RepeatStatus> = Callable {
        logger.info("This was executed in another thread")
        RepeatStatus.FINISHED
    }

    @Bean
    fun callableTasklet(): CallableTaskletAdapter {
        val callableTaskletAdapter = CallableTaskletAdapter()

        callableTaskletAdapter.setCallable(callableObject())
        return callableTaskletAdapter
    }

}