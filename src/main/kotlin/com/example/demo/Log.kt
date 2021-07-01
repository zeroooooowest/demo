package com.example.demo

import mu.KotlinLogging
import org.slf4j.Logger

interface Log {
    val logger: Logger get() = KotlinLogging.logger {}
}