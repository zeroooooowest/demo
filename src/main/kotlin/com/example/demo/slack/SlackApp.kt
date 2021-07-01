package com.example.demo.slack

import com.example.demo.Log
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse.SlashCommandResponseBuilder
import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.api.bolt.context.builtin.SlashCommandContext
import com.slack.api.bolt.request.builtin.SlashCommandRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors.joining


@Configuration
class SlackApp {

    companion object: Log

    @Bean
    fun appConfig(): AppConfig {
        val config = AppConfig()
//        val classLoader: ClassLoader = SlackApp::class.java.classLoader
//        // src/test/resources/appConfig.json
//        try {
//            classLoader.getResourceAsStream("appConfig.json").use { `is` ->
//                InputStreamReader(`is`).use { isr ->
//                    val json: String = BufferedReader(isr).lines().collect(joining())
//                    val j = Gson().fromJson(json, JsonElement::class.java).asJsonObject
//                    config.signingSecret = j["signingSecret"].asString
//                    config.singleTeamBotToken = j["singleTeamBotToken"].asString
//                }
//            }
//        } catch (e: IOException) {
//            logger.error(e.message, e)
//        }

        config.signingSecret = "w"
        config.singleTeamBotToken = "q"

        return config
    }

    @Bean
    fun initSlackApp(config: AppConfig): App {
        val app = App(config)
        app.command(
            "/hello"
        ) { req: SlashCommandRequest?, ctx: SlashCommandContext ->
            ctx.ack { r: SlashCommandResponseBuilder ->
                r.text(
                    "Thanks!"
                )
            }
        }
        return app
    }


}