plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":tamago-core"))

    implementation ("org.springframework.boot:spring-boot-starter-web")

    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2025.0.0"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("com.nimbusds:nimbus-jose-jwt:9.37.3")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
