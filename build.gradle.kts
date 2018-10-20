import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.example"
version = "0.1.0-SNAPSHOT"

plugins {
    val kotlinVersion = "1.2.71"
    val springBootVersion = "2.0.6.RELEASE"
    val springDependencyManagementVersion = "1.0.6.RELEASE"

    java
    jacoco
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
    reports {
        html.destination = file("$buildDir/reports/junit/html")
    }
    filter {
        includeTestsMatching("com.example.tictactoe.*")
    }
}

tasks {
    register("teste2e", Test::class) {
        useJUnitPlatform()
        reports {
            html.destination = file("$buildDir/reports/junite2e/html")
        }
        filter {
            includeTestsMatching("com.example.e2e.*")
        }
    }
}

jacoco {
    toolVersion = "0.8.2"
}

tasks {
    "jacocoTestReport"(JacocoReport::class) {
        reports {
            html.destination = file("$buildDir/reports/jacoco/html")
        }
    }
}

dependencies {
    val junitPlatformLauncherVersion = "1.3.1"
    val spekVersion = "1.1.5"
    val assertjVersion = "3.11.1"
    val restAssuredVersion = "3.1.1"

    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework:spring-webflux")
    compile("io.projectreactor.ipc:reactor-netty")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")


    testCompile("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.jetbrains.spek:spek-api:$spekVersion") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion") {
        exclude(group = "org.junit.platform")
        exclude(group = "org.jetbrains.kotlin")
    }
    testCompile("org.assertj:assertj-core:$assertjVersion")
    testCompile("io.rest-assured:rest-assured:$restAssuredVersion")
}

repositories {
    mavenCentral()
    maven("http://dl.bintray.com/jetbrains/spek")
}