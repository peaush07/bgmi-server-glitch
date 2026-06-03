# Download & Installation Guide

## 📱 How to Download and Install the App

### **3 Ways to Get the App:**

---

## **METHOD 1: Download APK (Android) - Easiest Way** ⚡

### **Step 1: Clone Repository**
```bash
git clone https://github.com/peaush07/bgmi-server-glitch.git
cd bgmi-server-glitch
```

### **Step 2: Build APK from Source**

**Option A: Using Android Studio (Recommended)**

```bash
1. Open Android Studio
2. File → Open → Select 'mobile/android' folder
3. Wait for Gradle sync (2-3 minutes)
4. Click Build → Build Bundle(s) / APK(s) → Build APK(s)
5. Wait for build to complete
6. Find APK at: mobile/android/app/build/outputs/apk/debug/app-debug.apk
```

**Option B: Using Command Line**

```bash
cd mobile/android
./gradlew assembleDebug
```

✅ **APK will be at:** `mobile/android/app/build/outputs/apk/debug/app-debug.apk`

### **Step 3: Transfer APK to Phone**

```bash
# Using USB connection
adb install mobile/android/app/build/outputs/apk/debug/app-debug.apk

# Or manually copy to phone and open
```

### **Step 4: Install on Phone**

1. **Transfer APK to Phone** (USB or email)
2. **Open File Manager** on phone
3. **Navigate to Downloads** folder
4. **Long press APK** → "Open with" → "Package Installer"
5. **Click Install**
6. **App installed!** ✅

---

## **METHOD 2: Download Pre-Built APK** 📦

### **From GitHub Releases (When Available)**

```bash
# Check releases at:
https://github.com/peaush07/bgmi-server-glitch/releases

# Download the latest APK file
# Transfer to phone and install as above
```

---

## **METHOD 3: Install via Android Studio Emulator** 💻

### **Step 1: Create Virtual Device**

```bash
1. Open Android Studio
2. Tools → AVD Manager (or device icon)
3. Click "Create Virtual Device"
4. Select Phone (e.g., Pixel 4)
5. Select Android version (API 30+)
6. Click "Finish"
```

### **Step 2: Run App**

```bash
1. Open Project: File → Open → mobile/android
2. Select your Virtual Device from dropdown
3. Press Shift+F10 (or Run button)
4. App will install automatically on emulator
```

✅ **App running in emulator!**

---

## **iOS App Installation** 🍎

### **METHOD 1: Install via Xcode (iOS Simulator)**

```bash
# Step 1: Open project
1. Xcode → File → Open
2. Select: mobile/ios/BGMIServerGlitch
3. Wait for indexing

# Step 2: Run on simulator
1. Select Simulator (e.g., iPhone 14) from dropdown
2. Press Cmd+R or Product → Run
3. App will build and run automatically
```

### **METHOD 2: Install on Physical iPhone**

```bash
# Step 1: Connect iPhone
1. Connect iPhone with USB cable
2. Trust this computer (on iPhone)

# Step 2: Configure signing
1. Xcode → Select Project
2. Select Target
3. Signing & Capabilities
4. Select your Team ID
5. Update Bundle Identifier if needed

# Step 3: Run on device
1. Select your iPhone from device dropdown
2. Press Cmd+R
3. First run may take time for provisioning
4. App installed on iPhone! ✅
```

### **METHOD 3: TestFlight (Beta Distribution)**

```bash
# Step 1: Create App in App Store Connect
# Step 2: Upload build via Xcode/Transporter
# Step 3: Send TestFlight link to testers
# Step 4: Testers download from TestFlight app
```

---

## **Admin Panel Installation** 💻

### **Step 1: Install Node.js**

```bash
# Download from: https://nodejs.org/
# LTS version recommended (14+)
```

### **Step 2: Setup Admin Panel**

```bash
cd admin-panel

# Install dependencies
npm install

# Create .env file
echo "REACT_APP_API_URL=http://localhost:8080/api" > .env
```

### **Step 3: Run Admin Panel**

```bash
npm start
```

✅ **Admin Panel opens at:** `http://localhost:3000`

---

## **Quick Start - Complete Flow**

### **For Android:**
```bash
# 1. Clone repo
git clone https://github.com/peaush07/bgmi-server-glitch.git

# 2. Build APK
cd mobile/android
./gradlew assembleDebug

# 3. Install on phone
adb install app/build/outputs/apk/debug/app-debug.apk

# 4. Open app and enjoy!
```

### **For iOS:**
```bash
# 1. Open in Xcode
open mobile/ios/BGMIServerGlitch/BGMIServerGlitch.xcworkspace

# 2. Select simulator/device
# 3. Press Cmd+R
# 4. Done!
```

### **For Backend:**
```bash
# 1. Navigate to backend
cd backend

# 2. Build and run
mvn spring-boot:run

# Backend running at: http://localhost:8080/api
```

---

## **System Requirements**

### **Android App**
- **Android Version:** 5.0 (API 21) or higher
- **RAM:** 2GB minimum
- **Storage:** 100MB free space
- **Permissions:** Internet, Storage (optional)

### **iOS App**
- **iOS Version:** 14.0 or higher
- **iPhone:** 6S or newer
- **Storage:** 150MB free space
- **Permissions:** Network access

### **Backend Server**
- **Java:** 17 or higher
- **RAM:** 512MB minimum (2GB recommended)
- **Database:** MySQL 8.0 or PostgreSQL 13+
- **Port:** 8080 (or configure in properties)

### **Admin Panel**
- **Browser:** Chrome, Firefox, Safari (latest)
- **Node.js:** 14+ LTS
- **npm:** 6+

---

## **Troubleshooting**

### **Android Issues**

**"App not installing"**
```bash
# Enable "Unknown Sources" in phone settings
Settings → Security → Unknown Sources → Enable
```

**"Build failed"**
```bash
# Clean and rebuild
cd mobile/android
./gradlew clean
./gradlew assembleDebug
```

**"API connection failed"**
```bash
# Check API URL in ApiConfig.kt
# Verify backend is running
# Check firewall allows port 8080
```

### **iOS Issues**

**"Code signing failed"**
```bash
# Select correct team ID in Xcode
# Signing & Capabilities → Select Team
```

**"Pod installation failed"**
```bash
cd mobile/ios/BGMIServerGlitch
pod deintegrate
pod install
```

**"Cannot connect to API"**
```bash
# For Simulator: http://localhost:8080/api
# For Device: http://YOUR_IP:8080/api
```

---

## **Detailed Setup Videos** 🎥

### **Android Setup:**
1. Clone repository
2. Open in Android Studio
3. Build APK
4. Install on device/emulator

### **iOS Setup:**
1. Open in Xcode
2. Install pods
3. Configure signing
4. Run on simulator/device

### **Backend Setup:**
1. Install Java
2. Configure database
3. Run Maven
4. Backend ready

---

## **Installation Checklist** ✅

### **Before Installation:**
- [ ] Git installed
- [ ] Android Studio (for Android) or Xcode (for iOS)
- [ ] Java 17+ installed
- [ ] Node.js installed
- [ ] USB cable (for physical device)

### **After Installation:**
- [ ] App opens successfully
- [ ] Can register account
- [ ] Can login
- [ ] Can see dashboard
- [ ] Backend API responding
- [ ] Admin panel accessible

---

## **Download Links**

### **Official Repository**
```
https://github.com/peaush07/bgmi-server-glitch
```

### **Java Download**
```
https://www.oracle.com/java/technologies/downloads/
```

### **Android Studio**
```
https://developer.android.com/studio
```

### **Xcode** (Mac Only)
```
https://apps.apple.com/us/app/xcode/id497799835
```

### **Node.js**
```
https://nodejs.org/en/
```

---

## **Support**

If you face any issues:
1. Check logs in Android Studio / Xcode
2. Verify all requirements are met
3. Check GitHub issues
4. Ensure backend is running
5. Verify network connectivity

---

## 🎉 Installation Complete!

Once installed, you can:
- ✅ Register new account
- ✅ Login with credentials
- ✅ Start server access
- ✅ Use stealth bypass system
- ✅ View active sessions
- ✅ Access admin panel

**Enjoy!** 🚀
