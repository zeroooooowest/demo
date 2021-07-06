package com.example.demo.batch.rest

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import java.util.*

class JobLaunchRequest {

    var name: String? = null

    var jobParamsProperties: Properties? = null

    fun jobParameters(): JobParameters = Properties().apply {
        jobParamsProperties?.apply(::putAll)
    }.let {
        JobParametersBuilder(it).toJobParameters()
    }


}