plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.8"
	id("io.spring.dependency-management") version "1.1.7"
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
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
