import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    id("io.spring.dependency-management") version "1.1.3" apply false
    id("org.springframework.boot") version "3.1.4" apply false
    id("nebula.integtest") version "9.6.3" apply false
}

allprojects {
    group = "allegro-hotel-room-reservations"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    dependencies {
        implementation(enforcedPlatform(kotlin("bom")))
        implementation(platform("org.springframework.boot:spring-boot-dependencies:3.1.4"))

        implementation(platform("org.junit:junit-bom:5.8.2"))
        implementation(platform("org.testcontainers:testcontainers-bom:1.16.2"))

        constraints {
            implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
            implementation("net.logstash.logback:logstash-logback-encoder:7.4")
            implementation("com.auth0:java-jwt:3.18.3")
            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("io.strikt:strikt-core:0.34.1")
            implementation("io.mockk:mockk:1.12.3")
            testImplementation("org.wiremock:wiremock-standalone:3.2.0")
        }
    }


    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }

        withType<Test> {
            useJUnitPlatform()
//            FOR USING PODMAN INSTEAD OF DOCKER
//            val os: OperatingSystem = DefaultNativePlatform.getCurrentOperatingSystem()
//            if (os.isLinux) {
//                val byteOut = ByteArrayOutputStream()
//                project.exec {
//                    commandLine("id", "-u")
//                    standardOutput = byteOut
//                }
//                val uid = String(byteOut.toByteArray())
//                environment("DOCKER_HOST", "unix:///run/user/$uid/podman/podman.sock")
//            } else if (os.isMacOsX()) {
//                environment("DOCKER_HOST", "unix:///tmp/podman.sock")
//            }
//            environment("TESTCONTAINERS_RYUK_DISABLED", "true")
        }
    }
}