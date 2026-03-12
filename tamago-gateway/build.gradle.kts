plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(":tamago-storage"))
    implementation(project(":tamago-core"))
    implementation(project(":tamago-oauth"))
    implementation(project(":tamago-aws"))
    implementation(project(":tamago-notification"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.modulith:spring-modulith-actuator")
    implementation("org.springframework.modulith:spring-modulith-observability")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.7")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
}
