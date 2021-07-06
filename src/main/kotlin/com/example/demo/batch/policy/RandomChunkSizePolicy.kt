package com.example.demo.batch.policy

import com.example.demo.Log
import org.springframework.batch.repeat.CompletionPolicy
import org.springframework.batch.repeat.RepeatContext
import org.springframework.batch.repeat.RepeatStatus
import java.util.*

class RandomChunkSizePolicy() : CompletionPolicy {

    companion object : Log

    private var chunkSize = 0
    private var totalProcessed = 0
    private var random = Random()

    override fun isComplete(context: RepeatContext, result: RepeatStatus): Boolean =
        if (RepeatStatus.FINISHED == result) true else isComplete(context)

    override fun isComplete(context: RepeatContext): Boolean = totalProcessed >= chunkSize

    override fun start(parent: RepeatContext): RepeatContext {
        chunkSize = random.nextInt(20)
        totalProcessed = 0

        logger.info("The chunk size has been set to $chunkSize")
        return parent
    }

    override fun update(context: RepeatContext) {
        totalProcessed++
    }

}