import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.springframework.boot") version "3.5.8"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
}

group = "tamago"
version = "0.0.1-SNAPSHOT"
description = "TamaGo Server"

val springModulithVersion = "1.4.3"

subprojects {
	apply(plugin = "io.spring.dependency-management")

	repositories {
		mavenCentral()
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.modulith:spring-modulith-bom:$springModulithVersion")
		}
	}

	plugins.withType<JavaPlugin> {
		extensions.configure<JavaPluginExtension> {
			toolchain {
				languageVersion.set(JavaLanguageVersion.of(21))
			}
		}
	}
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
	debug.set(true)
	verbose.set(true)
	outputToConsole.set(true)
	ignoreFailures.set(false)
	filter {
		exclude("**/generated/**")
	}
}

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<BootJar> {
	mainClass.set("tamago.server.gateway.TamaGoApplicationKt")
}
