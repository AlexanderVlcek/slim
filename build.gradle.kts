plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    val serenityVersion = "2.4.24"
    val cucumberVersion = "6.10.4"
    val junitVersion = "5.7.1"

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    testImplementation("io.cucumber:cucumber-java:${cucumberVersion}")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:${cucumberVersion}")
    implementation("com.typesafe:config:1.4.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")

    implementation("net.serenity-bdd:serenity-core:${serenityVersion}")
    implementation("net.serenity-bdd:serenity-rest-assured:${serenityVersion}")
//    implementation ("net.serenity-bdd:serenity-gradle-plugin:${serenityVersion}")

    // This dependency is used by the application.
//    implementation("com.google.guava:guava:30.0-jre")

//    compileOnly("org.projectlombok:lombok: 1.18.20")

    testImplementation("io.rest-assured:rest-assured:4.4.0")


}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

tasks {

    val consoleLauncherTest by registering(JavaExec::class) {
        dependsOn(testClasses)
        val reportsDir = file("$buildDir/test-results")
        outputs.dir(reportsDir)
        classpath = sourceSets["test"].runtimeClasspath
        main = "org.junit.platform.console.ConsoleLauncher"
        args("--scan-classpath")
        args("--include-engine", "cucumber")
        args("--reports-dir", reportsDir)
    }

    test {
        dependsOn(consoleLauncherTest)
        exclude("**/*")
    }
}