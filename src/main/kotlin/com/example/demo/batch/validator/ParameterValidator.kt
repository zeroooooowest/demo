package com.example.demo.batch.validator

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils

@Configuration
class ParameterValidator
//    : JobParametersValidator
{

//    override fun validate(parameters: JobParameters?) {
//        val fileName: String? = parameters?.getString("fileName")
//
//        if (!StringUtils.hasText(fileName)) {
//            throw JobParametersInvalidException("fileName parameter is missing")
//        } else if (!StringUtils.endsWithIgnoreCase(fileName, "csv")) {
//            throw JobParametersInvalidException("fileName parameter does not use the csv file extension")
//        }
//    }

//    @Bean
//    fun validator(): JobParametersValidator {
//        val validator = DefaultJobParametersValidator()
//
//        validator.setRequiredKeys(arrayOf("fileName"))
//        validator.setOptionalKeys(arrayOf("name"))
//
//        return validator
//    }
//
//    @Bean
//    fun validator(): CompositeJobParametersValidator {
//        val validator = CompositeJobParametersValidator()
//
//        val defaultJobParametersValidator = DefaultJobParametersValidator(arrayOf("fileName"), arrayOf("name"))
//
//        defaultJobParametersValidator.afterPropertiesSet()
//        validator.setValidators(
//            listOf(
//                JobParametersValidator { parameters ->
//                    val fileName = parameters?.getString("fileName")
//                    if (!StringUtils.hasText(fileName)) {
//                        throw JobParametersInvalidException("fileName parameter is missing")
//                    } else if (!StringUtils.endsWithIgnoreCase(fileName, "csv")) {
//                        throw JobParametersInvalidException("fileName parameter does not use the csv file extension")
//                    }
//                },
//                defaultJobParametersValidator
//            )
//        )
//        return validator
//    }

    @Bean
    fun validator(): CompositeJobParametersValidator {
        val validator = CompositeJobParametersValidator()

        val defaultJobParametersValidator = DefaultJobParametersValidator(
            arrayOf("fileName"), arrayOf("name", "run.id")
        )

        defaultJobParametersValidator.afterPropertiesSet()

        validator.setValidators(
            listOf(
                JobParametersValidator { parameters ->
                    val fileName = parameters?.getString("fileName")
                    if (!StringUtils.hasText(fileName)) {
                        throw JobParametersInvalidException("fileName parameter is missing")
                    } else if (!StringUtils.endsWithIgnoreCase(fileName, "csv")) {
                        throw JobParametersInvalidException("fileName parameter does not use the csv file extension")
                    }
                },
                defaultJobParametersValidator
            )
        )
        return validator
    }

}