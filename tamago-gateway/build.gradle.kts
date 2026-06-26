plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.sentry.jvm.gradle")
}

val hasSentryToken = System.getenv("SENTRY_AUTH_TOKEN") != null

sentry {
    includeSourceContext.set(hasSentryToken)
    org.set("tamago")
    projectName.set("tamago-server")
    authToken.set(System.getenv("SENTRY_AUTH_TOKEN"))
}

val sentryAgent: Configuration by configurations.creating

tasks.register<Copy>("copySentryAgent") {
    from(sentryAgent)
    into(layout.buildDirectory.dir("agent"))
    rename { "sentry-opentelemetry-agent.jar" }
}

tasks.named("build") {
    dependsOn("copySentryAgent")
}

dependencies {
    implementation(project(":tamago-storage"))
    implementation(project(":tamago-core"))
    implementation(project(":tamago-oauth"))
    implementation(project(":tamago-aws"))
    implementation(project(":tamago-notification"))
    implementation(project(":tamago-monitoring"))
    implementation(project(":tamago-weather"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.modulith:spring-modulith-actuator")
    implementation("org.springframework.modulith:spring-modulith-observability")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.7")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.sentry:sentry-spring-boot-starter-jakarta:8.31.0")
    implementation("io.sentry:sentry-opentelemetry-agent:8.31.0")

    sentryAgent("io.sentry:sentry-opentelemetry-agent:8.31.0")
}
