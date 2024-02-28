plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id ("io.freefair.lombok") version  "6.2.0"

}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    //add dependencies as needed
    implementation ("org.projectlombok:lombok:1.18.20")
    // Add annotation processor for Lombok
    annotationProcessor ("org.projectlombok:lombok:1.18.20")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
