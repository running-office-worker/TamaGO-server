plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.flywaydb.flyway") version "11.18.0"
}

val kotlinJdslVersion = "3.7.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate.orm:hibernate-spatial")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.modulith:spring-modulith-starter-core")

    // Kotlin JDSL
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:$kotlinJdslVersion")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:$kotlinJdslVersion")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:$kotlinJdslVersion")

    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.flywaydb:flyway-mysql")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    implementation("io.jsonwebtoken:jjwt:0.12.6")

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.7")

    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-mysql:11.18.0")
    }
}

flyway {
    url = "jdbc:mysql://localhost:3306/tamago?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
    user = System.getenv("DB_USERNAME")
    password = System.getenv("DB_PASSWORD")
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
