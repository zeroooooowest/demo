package com.example.demo.batch

import com.example.demo.Log
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class TutorialConfig {
    private val jobBuilderFactory: JobBuilderFactory? = null

    companion object: Log

//    @Bean
//    fun tutorialJob(): Job {
//        return jobBuilderFactory!!["tutorialJob"]
//            .start(tutorialStep1())
//            .next(tutorialStep2())
//            .build()
//    }
//
//    private fun tutorialStep1(): Step {
//
//    }
//
//    // step1 생략
//    @Bean
//    fun tutorialStep2(): Step {
//        return stepBuilderFactory.get("tutorialStep2")
//            .tasklet { contribution, chunkContext ->
//                logger.debug("I'm a tutorialStep2")
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
}