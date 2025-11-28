import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":core"))

    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation ("org.springframework.modulith:spring-modulith-starter-core")

    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.7")

    implementation("com.nimbusds:nimbus-jose-jwt:9.37.3")
}

springBoot {
    mainClass.set("tamago.server.gateway.TamaGoApplication")
}

tasks.named<BootJar>("bootJar") {
    archiveFileName.set("TamaGo-server.jar")
    mainClass.set("tamago.server.gateway.TamaGoApplication")
}

tasks.named<BootRun>("bootRun") {
    mainClass.set("tamago.server.gateway.TamaGoApplication")
}
