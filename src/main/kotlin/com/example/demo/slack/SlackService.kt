package com.example.demo.slack

import com.slack.api.Slack
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.request.files.FilesUploadRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.Future


@Service
@Async("taskExecutor")
class SlackService(
    @Value("\${slack.token}") private val token: String,
    @Autowired private val slack: Slack
) {

    fun sendMessage(field: Map<String, String>, messageLevel: SlackMessageLevel): Future<Boolean> {

        val text = StringBuilder().apply {
            field.forEach {
                append("*${it.key}*")
                append("\n    ${it.value}\n\n")
            }
        }.toString()

        val chatPostMessageResponse = slack.methodsAsync(token).chatPostMessage { it
            .channel("#bot-test")
            .text(text)
        }

        return AsyncResult(chatPostMessageResponse.isDone)
    }

    fun sendImage(initialComment: String, imagePath: String): Future<Boolean> {

        val filesUploadResponse = slack.methodsAsync(token).filesUpload { it
            .fileData(Files.readAllBytes(Paths.get(imagePath)))
            .content(MediaType.MULTIPART_FORM_DATA_VALUE)
            .initialComment(initialComment)
            .channels(listOf("#bot-test"))
        }

        return AsyncResult(filesUploadResponse.isDone)
    }


}