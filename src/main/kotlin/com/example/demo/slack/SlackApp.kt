package com.example.demo.slack

import com.slack.api.Slack
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SlackApp {


    @Bean
    fun slack(): Slack = Slack.getInstance()


//    @Bean
//    fun my(){
//        val slack: Slack = Slack.getInstance()
//
//    }
}