# Kotlin Desktop Starter

A small Kotlin desktop application built with Compose Multiplatform. It opens a native window and includes a working button as a starting point for your UI.

## Requirements

- JDK 17 or newer
- Gradle 9.2.1 or newer

## Run

From the project folder:

```powershell
gradle run
```

## Build a native installer

```powershell
gradle packageDistributionForCurrentOS
```

The installer is written under `build/compose/binaries`.

## Project layout

```text
src/main/kotlin/com/example/app/Main.kt  App entry point and starter screen
build.gradle.kts                         Kotlin, Compose, and packaging setup
settings.gradle.kts                      Gradle project configuration
```
