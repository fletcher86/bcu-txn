plugins {
    id("groovy")
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.4.0"
    id("org.springframework.boot") version "3.5.14"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.fletcher"
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    // withSourcesJar()
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.liquibase:liquibase-core")
    implementation("org.slf4j:slf4j-api")
    implementation("ch.qos.logback:logback-classic")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.mysql:mysql-connector-j")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-collections4")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("commons-beanutils:commons-beanutils:1.11.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
    implementation("com.squareup.okhttp3:okhttp:5.4.0")

    testImplementation("org.spockframework:spock-core:2.3-groovy-4.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("junit:junit")
    testImplementation("de.bwaldvogel:mongo-java-server:1.47.0")
}

springBoot {
    buildInfo()
}


tasks.bootJar {
    enabled = true
    // manifest { ... }
}

tasks.jar {
    enabled = false
}

tasks.bootRun {
    systemProperties.putAll(System.getProperties().map { it.key.toString() to it.value })
}

tasks.test {
    useJUnitPlatform()
}
