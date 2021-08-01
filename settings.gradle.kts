
pluginManagement {
    val springbootVersion: String by settings
    val kotlinVersion: String by settings
    val springDependencyManagementVersion: String by settings

    plugins {
        id("org.springframework.boot") version springbootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion
    }
}