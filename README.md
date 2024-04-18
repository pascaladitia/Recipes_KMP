This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

**Tech Stack Highlights**
- voyager = "1.0.0"
- kotlinx-coroutines = "1.8.0"
- ktor = "2.3.9"
- kermit = "2.0.2"
- composeIcons = "1.1.0"
- kotlinx-serialization = "1.6.3"
- kotlinx-datetime = "0.6.0-RC.2"
- moko-mvvm = "0.16.1"
- moko-permission = "0.17.0"
- kamelImage = "0.9.3"
- buildConfig = "4.1.1"
- sqlDelight = "2.0.1"
- multiplatformSettings = "1.1.1"
- preferenceKtx = "1.2.1"
- peekaboo = "0.5.1"
- pagingCommon = "3.3.0-alpha02-0.4.0"
- pagingComposeCommon = "3.3.0-alpha02-0.4.0"

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
