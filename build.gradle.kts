plugins {
    java
    kotlin("jvm") version "1.4.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "jitpack.io"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")

    implementation("javax.mail:mail:1.4.1")
    implementation("com.googlecode.json-simple:json-simple:1.1")
    implementation("org.jsoup:jsoup:1.8.3")
    implementation("com.github.jkcclemens:khttp:-SNAPSHOT")
    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
}
