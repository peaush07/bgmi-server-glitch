# Quick Start Script

## One-Command Installation (Linux/Mac)

```bash
#!/bin/bash

# BGMI Server Glitch - Quick Setup

echo "================================"
echo "BGMI Server Glitch - Setup"
echo "================================"

# Check if git is installed
if ! command -v git &> /dev/null; then
    echo "Git is not installed. Please install Git first."
    exit 1
fi

# Clone repository
echo "Cloning repository..."
git clone https://github.com/peaush07/bgmi-server-glitch.git
cd bgmi-server-glitch

echo ""
echo "Repository cloned successfully!"
echo ""
echo "Select what you want to setup:"
echo "1. Android App"
echo "2. iOS App"
echo "3. Backend Server"
echo "4. Admin Panel"
echo "5. All"
echo ""
read -p "Enter your choice (1-5): " choice

case $choice in
    1)
        echo "Setting up Android App..."
        cd mobile/android
        echo "Building APK..."
        ./gradlew assembleDebug
        echo "APK built at: app/build/outputs/apk/debug/app-debug.apk"
        ;;
    2)
        echo "Setting up iOS App..."
        cd mobile/ios/BGMIServerGlitch
        echo "Installing pods..."
        pod install
        echo "iOS setup complete! Open in Xcode."
        ;;
    3)
        echo "Setting up Backend Server..."
        cd backend
        echo "Installing Maven dependencies..."
        mvn clean install
        echo "Backend setup complete! Run with: mvn spring-boot:run"
        ;;
    4)
        echo "Setting up Admin Panel..."
        cd admin-panel
        echo "Installing npm packages..."
        npm install
        echo "Admin panel setup complete! Run with: npm start"
        ;;
    5)
        echo "Setting up all components..."
        # Android
        cd mobile/android && ./gradlew assembleDebug && cd ../..
        # iOS
        cd mobile/ios/BGMIServerGlitch && pod install && cd ../../..
        # Backend
        cd backend && mvn clean install && cd ..
        # Admin
        cd admin-panel && npm install && cd ..
        echo "All components setup complete!"
        ;;
    *)
        echo "Invalid choice"
        exit 1
        ;;
esac

echo ""
echo "================================"
echo "Setup Complete!"
echo "================================"
```

## Windows Batch Script

```batch
@echo off
echo ================================
echo BGMI Server Glitch - Setup
echo ================================

REM Check if git is installed
git --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Git is not installed. Please install Git first.
    pause
    exit /b 1
)

REM Clone repository
echo Cloning repository...
git clone https://github.com/peaush07/bgmi-server-glitch.git
cd bgmi-server-glitch

echo.
echo Select what you want to setup:
echo 1. Android App
echo 2. Backend Server
echo 3. Admin Panel
echo 4. All
echo.
set /p choice="Enter your choice (1-4): "

if "%choice%"=="1" (
    echo Setting up Android App...
    cd mobile\android
    echo Building APK...
    gradlew.bat assembleDebug
    echo APK built at: app\build\outputs\apk\debug\app-debug.apk
) else if "%choice%"=="2" (
    echo Setting up Backend Server...
    cd backend
    echo Installing Maven dependencies...
    mvn clean install
    echo Backend setup complete!
) else if "%choice%"=="3" (
    echo Setting up Admin Panel...
    cd admin-panel
    echo Installing npm packages...
    npm install
    echo Admin panel setup complete!
) else if "%choice%"=="4" (
    echo Setting up all components...
    cd mobile\android
    gradlew.bat assembleDebug
    cd ..\..
    cd backend
    mvn clean install
    cd ..
    cd admin-panel
    npm install
    cd ..
) else (
    echo Invalid choice
    pause
    exit /b 1
)

echo.
echo ================================
echo Setup Complete!
echo ================================
pause
```

## Standalone Download

### Pre-compiled APK Download

Download pre-built APK (when released):
```
https://github.com/peaush07/bgmi-server-glitch/releases
```

Then:
1. Download APK file
2. Transfer to Android phone
3. Enable "Unknown Sources" in Security settings
4. Install APK
5. Done!

