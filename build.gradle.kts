import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.8"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // jfreechart
    implementation("org.jfree:jfreechart:1.5.3")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    // batch
    implementation("org.springframework.boot:spring-boot-starter-batch")

    // quartz
//    implementation("org.springframework.boot:spring-boot-starter-quartz")

    runtimeOnly("com.h2database:h2:1.4.200")

    // shedlock
    implementation("net.javacrumbs.shedlock:shedlock-spring:4.24.0")
//    implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:4.24.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:4.24.0")


    // slack
    implementation("com.slack.api:slack-api-client:1.8.1")
    implementation("com.slack.api:slack-api-model-kotlin-extension:1.8.1")
    implementation("com.slack.api:slack-api-client-kotlin-extension:1.8.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
