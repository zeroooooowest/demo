package com.example.demo.batch

import com.example.demo.Log
import com.example.demo.slack.SlackMessageLevel
import com.example.demo.slack.SlackService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetAddress

@Configuration
class MethodInvokingTaskletConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
    @Autowired private val slackService: SlackService,
) {

    companion object : Log

    @Bean
    fun methodInvokingJob(): Job = jobBuilderFactory.get("methodInvokingJob")
        .start(methodInvokingStep())
        .build()


    @Bean
    fun methodInvokingStep(): Step = stepBuilderFactory.get("methodInvokingStep")
        .tasklet(methodInvokingTasklet())
        .build()

    @Bean
    fun methodInvokingTasklet(): MethodInvokingTaskletAdapter {
        val methodInvokingTaskletAdapter = MethodInvokingTaskletAdapter()

        methodInvokingTaskletAdapter.setTargetObject(slackService)
        methodInvokingTaskletAdapter.setTargetMethod("sendMessage")
        methodInvokingTaskletAdapter.setArguments(
            arrayOf(
                mapOf(
                    "message" to "Application READY",
                    "application" to "bun-cruiser-api",
                    "ip_address" to InetAddress.getLocalHost().hostAddress
                ),
                SlackMessageLevel.INFO
            )
        )
        return methodInvokingTaskletAdapter
    }
}