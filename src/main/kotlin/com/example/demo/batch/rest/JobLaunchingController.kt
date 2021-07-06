package com.example.demo.batch.rest

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class JobLaunchingController(
    @Autowired private val jobLauncher: JobLauncher,
    @Autowired private val applicationContext: ApplicationContext,
) {

    @PostMapping("/run")
    fun runJob(@RequestBody jobLaunchRequest: JobLaunchRequest): ExitStatus =
        applicationContext.getBean(jobLaunchRequest.name!!, Job::class.java)
            .let {
                jobLauncher.run(it, jobLaunchRequest.jobParameters()).exitStatus
            }
}