import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val logback_version: String by project
val ktor_version: String by project
val kotlin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.3.50"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

application {
    mainClassName = "com.example.ApplicationKt"
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.postgresql:postgresql:42.2.8")
    implementation("com.google.cloud.sql:postgres-socket-factory:1.0.16")
    implementation("com.zaxxer:HikariCP:3.4.3")
    implementation(platform("org.jdbi:jdbi3-bom:3.13.0"))
    implementation("org.jdbi:jdbi3-core")
    implementation("org.jdbi:jdbi3-kotlin")
    implementation("org.jdbi:jdbi3-postgres")
    implementation("com.google.cloud:google-cloud-tasks:1.29.1")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.assemble {
    dependsOn(tasks.shadowJar) // assembleの依存タスクとしてshadowJarを実行する。
}

tasks.shadowJar {
    archiveClassifier.set("") // fat jarを単独のjarと同じ名前にして上書きする。
    mergeServiceFiles() // https://github.com/grpc/grpc-java/issues/5493#issuecomment-478500418
}
