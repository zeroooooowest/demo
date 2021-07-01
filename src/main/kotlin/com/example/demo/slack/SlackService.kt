package com.example.demo.slack

import com.slack.api.bolt.App
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


@Service
class SlackService(
    @Value("\${slack.webhook-url}") private val webhookUrl: String,
    @Autowired private val app: App
) {

}