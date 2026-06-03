# Android Native App

## Overview
Native Android application built with Kotlin for the BGMI Server Glitch system.

## Prerequisites
- Android Studio (Latest version)
- Android SDK 21 or higher
- Kotlin 1.8+
- Gradle 8.0+

## Project Setup

### 1. Create New Android Project
Open Android Studio:
1. File → New → New Android Project
2. Select "Empty Activity" template
3. Configure:
   - Name: BGMI Server Glitch
   - Package: com.bgmi.serverglitch
   - Save location: `mobile/android/`
   - Language: Kotlin
   - Minimum SDK: API 21

### 2. Build Configuration
See `build.gradle` files for dependencies and configuration.

## Project Structure

```
mobile/android/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/bgmi/serverglitch/
│   │   │   │   ├── activities/
│   │   │   │   ├── fragments/
│   │   │   │   ├── viewmodels/
│   │   │   │   ├── services/
│   │   │   │   ├── models/
│   │   │   │   ├── network/
│   │   │   │   └── utils/
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   ├── values/
│   │   │   │   ├── drawable/
│   │   │   │   └── menu/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle
├── gradle/
└── settings.gradle
```

## Key Features

- User Authentication (Login/Register)
- Server Name Input
- 5-Minute Free Trial Timer
- Real-time Countdown Display
- API Key Management
- Season Selection
- Premium Access Purchase
- User Dashboard
- Session Management

## Dependencies

- Retrofit 2 - HTTP client
- OkHttp - Network interceptor
- LiveData & ViewModel - MVVM architecture
- Room - Local database
- Hilt - Dependency injection
- Material Design 3
- Coroutines - Async operations

## Building & Running

### Build
```bash
./gradlew build
```

### Run on Emulator
```bash
./gradlew installDebug
```

### Run on Device
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## API Configuration

Update `com/bgmi/serverglitch/network/ApiClient.kt`:
```kotlin
const val BASE_URL = "http://your-backend-url/api/"
```

## Architecture

Follows MVVM (Model-View-ViewModel) pattern:
- **Models**: Data classes
- **ViewModels**: Business logic
- **Activities/Fragments**: UI layer
- **Services**: API calls

## Testing

```bash
./gradlew test
./gradlew connectedAndroidTest
```

## Troubleshooting

- **Build fails**: Run `./gradlew clean build`
- **Sync issues**: File → Sync Now
- **Emulator issues**: Create new AVD in AVD Manager
