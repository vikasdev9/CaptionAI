pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Caption AI"
include(":app")
include(":core")
include(":core-ui")
include(":data")
include(":domain")
include(":feature-home")
include(":feature-caption")
include(":feature-hashtag")
include(":feature-bio")
include(":feature-reels")
include(":feature-planner")
include(":firebase")
include(":database")
include(":network")
