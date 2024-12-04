import org.apache.tools.ant.taskdefs.condition.Os
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    kotlin("jvm") version "2.1.0"
    jacoco
    application
    id("com.diffplug.spotless") version "6.25.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

application {
    mainClass = "pokemongame.PokemonGameKt"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

if (Os.isFamily(Os.FAMILY_MAC)) {
    application.applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
}

dependencies {
    implementation("io.arrow-kt:arrow-core:1.2.4")

    testImplementation("io.kotest:kotest-runner-junit5:6.0.0.M1")
    testImplementation("io.kotest:kotest-assertions-core:6.0.0.M1")
    testImplementation("io.kotest:kotest-assertions-collections:6.0.0.M1")
    testImplementation("io.kotest:kotest-assertions-arrow:6.0.0.M1")
    testImplementation("io.mockk:mockk:1.13.10")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt-config.yml")
    // baseline = file "$projectDir/config/baseline.xml"
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktfmt().kotlinlangStyle()
    }
}

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }

    register("format") {
        dependsOn("spotlessApply")
    }

    withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget = "17"
    }

    withType<Detekt>().configureEach {
        dependsOn("spotlessApply")
        jvmTarget = "17"

        reports {
            html.required.set(true)
            txt.required.set(true)
        }
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = 0.9.toBigDecimal()
                }
            }
        }

        classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                exclude ("**/PokemonGame.class")
                exclude ("**/pokemon/*.kt")
                exclude ("**/moves/*/kt")
            }
        )
    }

    jacocoTestReport {
        dependsOn("test")

        reports {
            html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
        }
    }

    named("check").get().dependsOn("jacocoTestCoverageVerification", "spotlessApply")
    named("build").get().dependsOn("jacocoTestReport")
}
