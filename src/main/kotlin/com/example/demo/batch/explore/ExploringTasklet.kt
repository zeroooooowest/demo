package com.example.demo.batch.explore

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class ExploringTasklet(
    private val jobExplorer: JobExplorer
): Tasklet {

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        TODO("Not yet implemented")
    }
}