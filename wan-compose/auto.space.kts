/**
 * JetBrains Space Automation
 * This Kotlin script file lets you automate build activities
 * For more info, see https://www.jetbrains.com/help/space/automation.html
 */


job("Build and run tests") {
    startOn {
        gitPush { enabled = false }
    }
    container(displayName = "Gradle build", image = "amazoncorretto:17-alpine") {
        kotlinScript { api ->
            // here goes complex logic
            if (api.gitBranch() == "refs/heads/master") {
                api.gradlew("build")
            } else {
                println("Running in custom branch")
            }
        }
    }
}