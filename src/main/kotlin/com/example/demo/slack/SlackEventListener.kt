//package com.example.demo.slack
//
//import org.slf4j.MDC
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.context.event.ApplicationFailedEvent
//import org.springframework.boot.context.event.ApplicationReadyEvent
//import org.springframework.context.event.EventListener
//import org.springframework.stereotype.Component
//import java.net.InetAddress
//
//@Component
//class SlackEventListener(
//    @Autowired private val slackService: SlackService
//) {
//
//    @EventListener(ApplicationReadyEvent::class)
//    fun afterApplicationReady() {
//        MDC.clear()
//        MDC.put("message", "APPLICATION READY")
//        MDC.put("application", "bun-cruiser-api")
//        MDC.put("ip_address", InetAddress.getLocalHost().hostAddress)
//        println("ready")
//        slackService.sendMessage(MDC.getCopyOfContextMap(), SlackMessageLevel.INFO)
//    }
//
//
//    @EventListener(ApplicationFailedEvent::class)
//    fun afterApplicationFailed() {
//        MDC.clear()
//        MDC.put("message", "APPLICATION FAILED")
//        MDC.put("application", "bun-cruiser-api")
//        MDC.put("ip_address", InetAddress.getLocalHost().hostAddress)
//        println("fail")
//        slackService.sendMessage(MDC.getCopyOfContextMap(), SlackMessageLevel.ERROR)
//    }
//
//}