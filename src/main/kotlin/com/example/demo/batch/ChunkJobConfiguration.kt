package com.example.demo.batch

import com.example.demo.Log
import com.example.demo.batch.policy.RandomChunkSizePolicy
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.CompletionPolicy
import org.springframework.batch.repeat.policy.CompositeCompletionPolicy
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy
import org.springframework.batch.repeat.policy.TimeoutTerminationPolicy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class ChunkJobConfiguration(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    companion object : Log

    @Bean
    fun chunkBasedJob(): Job = jobBuilderFactory.get("chunkBasedJob")
        .start(chunkStep())
        .build()

    @Bean
    fun chunkStep(): Step = stepBuilderFactory.get("chunkStep")
        .chunk<String, String>(RandomChunkSizePolicy())
        .reader(chunkItemReader())
        .writer(chunkItemWriter())
        .build()

    @Bean
    fun chunkItemReader(): ListItemReader<String> = ListItemReader<String>(
        List(100) { UUID.randomUUID().toString() }
    )

    @Bean
    fun chunkItemWriter(): ItemWriter<String> = ItemWriter { items ->
        for (item in items) {
            logger.info(">>>>> current item = $item")
        }
        logger.info("!!!!!!!!!!!!Commit!!!!!!!!!!!")
    }

    @Bean
    fun completionPolicy(): CompletionPolicy = CompositeCompletionPolicy().also {
        it.setPolicies(
            arrayOf(
                TimeoutTerminationPolicy(3),
                SimpleCompletionPolicy(1000)
            )
        )
    }
}
