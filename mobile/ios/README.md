# iOS Native App

## Overview
Native iOS application built with Swift and SwiftUI for the BGMI Server Glitch system.

## Prerequisites
- Xcode 14.0 or higher
- iOS 14.0 or higher
- Swift 5.7+
- CocoaPods (for dependency management)

## Project Setup

### 1. Create New iOS Project
Open Xcode:
1. File → New → Project
2. Select "App" template
3. Configure:
   - Product Name: BGMI Server Glitch
   - Team ID: Your Apple Developer Team
   - Organization Identifier: com.bgmi
   - Bundle Identifier: com.bgmi.serverglitch
   - Interface: SwiftUI
   - Minimum Deployment Target: iOS 14.0
   - Save location: `mobile/ios/`

### 2. Install Dependencies

```bash
cd mobile/ios
cd BGMIServerGlitch
pod init
```

Add to `Podfile`:
```ruby
pod 'Alamofire'
pod 'SwiftyJSON'
```

Then run:
```bash
pod install
open BGMIServerGlitch.xcworkspace
```

## Project Structure

```
mobile/ios/BGMIServerGlitch/
├── BGMIServerGlitch/
│   ├── App/
│   │   └── BGMIServerGlitchApp.swift
│   ├── Models/
│   │   ├── User.swift
│   │   ├── AuthResponse.swift
│   │   └── ServerAccess.swift
│   ├── Views/
│   │   ├── Auth/
│   │   │   ├── LoginView.swift
│   │   │   └── RegisterView.swift
│   │   └── Dashboard/
│   │       ├── DashboardView.swift
│   │       └── ServerAccessView.swift
│   ├── ViewModels/
│   │   ├── AuthViewModel.swift
│   │   └── ServerAccessViewModel.swift
│   ├── Services/
│   │   ├── APIService.swift
│   │   └── TokenManager.swift
│   ├── Network/
│   │   ├── APIClient.swift
│   │   └── APIConfig.swift
│   └── Utils/
│       └── Constants.swift
├── BGMIServerGlitchTests/
└── BGMIServerGlitch.xcodeproj/
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

## Architecture

Follows MVVM (Model-View-ViewModel) pattern with SwiftUI:
- **Models**: Data structures
- **ViewModels**: Business logic (Observable objects)
- **Views**: SwiftUI components
- **Services**: API calls and local storage

## Dependencies

- **Alamofire** - HTTP networking
- **SwiftyJSON** - JSON parsing
- **SwiftUI** - UI framework (built-in)
- **Combine** - Reactive programming (built-in)

## API Configuration

Update `Network/APIConfig.swift`:
```swift
let BASE_URL = "http://your-backend-url/api/"
```

## Building & Running

### Run on Simulator
1. Select simulator from Xcode
2. Press Cmd+R or Product → Run

### Run on Device
1. Connect physical iPhone
2. Select device from Xcode
3. Press Cmd+R or Product → Run

### Build for Archive
```bash
xcodebuild -scheme BGMIServerGlitch archive
```

## Testing

```bash
xcodebuild test -scheme BGMIServerGlitch
```

## Code Signing

1. Select project in Xcode
2. Select target
3. Go to Signing & Capabilities
4. Select your Team ID
5. Update Bundle Identifier if needed

## Troubleshooting

- **Build fails**: Clean build folder (Cmd+Shift+K)
- **Pod issues**: Run `pod deintegrate && pod install`
- **Deployment issues**: Check provisioning profile in Signing
- **API connection**: Verify BASE_URL in APIConfig.swift

## Publishing to App Store

1. Create app in App Store Connect
2. Configure signing certificate
3. Build and archive
4. Upload via Transporter
5. Submit for review
