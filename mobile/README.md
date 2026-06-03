# Mobile Applications

## Overview
Mobile applications for users to access servers with API keys and manage their sessions.

## Available Options

### 1. React Native (Cross-platform)
- **Pros**: Single codebase for iOS and Android
- **Setup**: `npx react-native init BGMIApp`
- **Path**: `./react-native/`

### 2. Native Android
- **Language**: Kotlin/Java
- **Path**: `./android/`
- **Build**: Android Studio

### 3. Native iOS
- **Language**: Swift
- **Path**: `./ios/`
- **Build**: Xcode

## Features

- User authentication
- Server name input
- 5-minute free trial
- Real-time countdown timer
- API key management
- Season selection
- Purchase additional time
- User dashboard

## API Integration

All mobile apps connect to the backend API at:
```
http://your-backend-url/api
```

## Getting Started

Choose one of the platforms above and follow the setup guide in its respective directory.
