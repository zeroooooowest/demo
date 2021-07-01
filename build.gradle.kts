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
    implementation("org.jfree:jfreechart:1.5.3")
    implementation("io.github.microutils:kotlin-logging:1.12.5")

    implementation("org.springframework.boot:spring-boot-starter-batch")

    // slack
    implementation("com.slack.api:bolt:1.8.1")
    implementation("com.slack.api:slack-api-client:1.8.1")
    implementation("com.slack.api:slack-api-model-kotlin-extension:1.8.1")
    implementation("com.slack.api:slack-api-client-kotlin-extension:1.8.1")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.slack.api:bolt-servlet:1.8.1")

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
