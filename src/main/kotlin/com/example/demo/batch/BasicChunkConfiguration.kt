package com.example.demo.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.mapping.PassThroughLineMapper
import org.springframework.batch.item.file.transform.PassThroughLineAggregator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

@Configuration
class BasicChunkConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun chunkJob(): Job = jobBuilderFactory.get("chunkJob")
        .start(chunkStep1())
        .build()

    @Bean
    fun chunkStep1(): Step = stepBuilderFactory.get("chunkStep1")
        .chunk<String, String>(10)
        .reader(basicChunkItemReader())
        .writer(basicChunkItemWriter())
        .build()

    @Bean
    @StepScope
    fun basicChunkItemReader(
        @Value("#{jobParameters['inputFile']}") inputFile: Resource? = null
    ) = FlatFileItemReaderBuilder<String>()
        .name("basicChunkItemReader")
        .resource(inputFile!!)
        .lineMapper(PassThroughLineMapper())
        .build()

    @Bean
    @StepScope
    fun basicChunkItemWriter(
        @Value("#{jobParameters['outputFile']}") outputFile: Resource? = null
    ) = FlatFileItemWriterBuilder<String>()
        .name("basicChunkItemWriter")
        .resource(outputFile!!)
        .lineAggregator(PassThroughLineAggregator())
        .build()

}