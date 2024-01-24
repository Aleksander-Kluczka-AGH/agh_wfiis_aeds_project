import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"

    id("io.spring.dependency-management") version "1.1.3"
    id("org.springframework.boot") version "3.1.4"
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("io.micrometer:micrometer-registry-prometheus")
//    implementation("net.logstash.logback:logstash-logback-encoder")

    implementation("org.postgresql:postgresql")

//    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

//    implementation("com.auth0:java-jwt")

//    testImplementation("org.junit.jupiter:junit-jupiter-api")
//    testImplementation("io.strikt:strikt-core")
//    testImplementation("io.mockk:mockk")

//    integTestImplementation("org.springframework.boot:spring-boot-starter-test")
//    integTestImplementation("org.springframework.security:spring-security-test")
//    integTestImplementation("org.testcontainers:mongodb")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

repositories {
    mavenCentral()
}

tasks {
    bootJar {
        archiveFileName.set("reservations-service.jar")
    }
}