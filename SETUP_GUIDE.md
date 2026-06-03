# Complete Setup & Installation Guide

## Project Overview
BGMI Server Glitch is a full-stack application with:
- **Backend**: Java Spring Boot REST API
- **Mobile Apps**: Android (Kotlin) and iOS (Swift)
- **Admin Panel**: React Dashboard

---

## 📋 Prerequisites

### For Backend
- Java 17 or higher
- Maven 3.8+
- MySQL 8.0 or PostgreSQL 13+
- Postman (for API testing)

### For Android
- Android Studio (Latest version)
- Android SDK 21 or higher
- Kotlin 1.8+

### For iOS
- Xcode 14.0 or higher
- iOS 14.0 or higher
- CocoaPods

### For Admin Panel
- Node.js 16+ and npm
- React knowledge

---

## 🚀 Backend Setup (Java Spring Boot)

### Step 1: Clone & Navigate
```bash
git clone https://github.com/peaush07/bgmi-server-glitch.git
cd bgmi-server-glitch/backend
```

### Step 2: Database Setup

**For MySQL:**
```sql
CREATE DATABASE bgmi_server;
USE bgmi_server;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE api_keys (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    key_value VARCHAR(255) NOT NULL UNIQUE,
    key_name VARCHAR(100),
    season VARCHAR(50),
    expires_at TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE server_access (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    server_name VARCHAR(100) NOT NULL,
    access_duration_millis BIGINT NOT NULL,
    access_type ENUM('FREE_TRIAL', 'PREMIUM', 'SEASONAL') DEFAULT 'FREE_TRIAL',
    status ENUM('ACTIVE', 'EXPIRED', 'STOPPED') DEFAULT 'ACTIVE',
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Step 3: Configure Database

Edit `src/main/resources/application.properties`:
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/bgmi_server
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT
jwt.secret=your-secret-key-change-this-in-production-12345
jwt.expiration=86400000

# Server
server.port=8080
server.servlet.context-path=/api
```

### Step 4: Build & Run
```bash
# Install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

✅ **Backend running at**: `http://localhost:8080/api`

### Step 5: Test API Endpoints

**Register User:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

---

## 📱 Android App Setup

### Step 1: Open Project in Android Studio
```bash
1. Open Android Studio
2. File → Open
3. Select: bgmi-server-glitch/mobile/android
4. Wait for Gradle sync
```

### Step 2: Update API Configuration

Edit `app/src/main/java/com/bgmi/serverglitch/network/ApiConfig.kt`:
```kotlin
const val BASE_URL = "http://your-machine-ip:8080/api/"
// For emulator: http://10.0.2.2:8080/api/
// For device: http://YOUR_MACHINE_IP:8080/api/
```

### Step 3: Run on Emulator
```bash
1. Click on AVD Manager
2. Select an emulator (or create new one)
3. Press Play button
4. In Android Studio, click Run → Run 'app'
5. Or press Shift + F10
```

### Step 4: Run on Physical Device
```bash
1. Connect Android phone with USB cable
2. Enable "Developer Mode" on phone
3. Enable "USB Debugging"
4. Select device in Android Studio dropdown
5. Click Run (Shift + F10)
```

### Testing on Android
- **Login Screen**: Enter email and password
- **Register**: Create new account
- **Dashboard**: Start server, select season, set duration
- **Timer**: Countdown display
- **Active Sessions**: View running sessions

---

## 🍎 iOS App Setup

### Step 1: Open Project in Xcode
```bash
1. Open Xcode
2. File → Open
3. Select: bgmi-server-glitch/mobile/ios/BGMIServerGlitch
4. Wait for indexing
```

### Step 2: Install Dependencies (CocoaPods)
```bash
cd mobile/ios/BGMIServerGlitch
pod install
open BGMIServerGlitch.xcworkspace
```

### Step 3: Update API Configuration

Edit `Network/APIConfig.swift`:
```swift
let baseURL = "http://your-machine-ip:8080/api/"
// For simulator: http://localhost:8080/api/
// For device: http://YOUR_MACHINE_IP:8080/api/
```

### Step 4: Run on Simulator
```bash
1. Select simulator from Xcode (e.g., iPhone 14)
2. Press Cmd+R or Product → Run
3. Wait for app to build and launch
```

### Step 5: Run on Physical Device
```bash
1. Connect iPhone with USB cable
2. Select device from Xcode
3. Select your Team ID (Signing & Capabilities)
4. Press Cmd+R
```

---

## 🎨 Admin Panel Setup (React)

### Step 1: Create React App
```bash
cd admin-panel
npx create-react-app .
cd .
```

### Step 2: Install Dependencies
```bash
npm install axios react-router-dom react-icons
npm install --save-dev tailwindcss
```

### Step 3: Create .env File
```bash
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ADMIN_TOKEN=your-admin-jwt-token
```

### Step 4: Basic Admin Dashboard Component

Create `src/pages/Dashboard.jsx`:
```jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Dashboard = () => {
  const [users, setUsers] = useState([]);
  const [apiKeys, setApiKeys] = useState([]);
  const [loading, setLoading] = useState(false);

  const API_URL = process.env.REACT_APP_API_URL;
  const token = localStorage.getItem('adminToken');

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_URL}/admin/users`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    } finally {
      setLoading(false);
    }
  };

  const generateAPIKey = async (userId) => {
    try {
      const response = await axios.post(
        `${API_URL}/admin/generate-key`,
        {
          userId,
          keyName: `Key-${Date.now()}`,
          season: 'Season 1',
          expirationDays: 30
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert(`API Key Generated: ${response.data.keyValue}`);
      fetchUsers();
    } catch (error) {
      console.error('Error generating key:', error);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold mb-6">Admin Dashboard</h1>
      
      <div className="bg-white rounded-lg shadow">
        <table className="w-full">
          <thead>
            <tr className="bg-gray-100 border-b">
              <th className="p-3 text-left">Username</th>
              <th className="p-3 text-left">Email</th>
              <th className="p-3 text-left">Role</th>
              <th className="p-3 text-center">Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id} className="border-b hover:bg-gray-50">
                <td className="p-3">{user.username}</td>
                <td className="p-3">{user.email}</td>
                <td className="p-3">{user.role}</td>
                <td className="p-3 text-center">
                  <button
                    onClick={() => generateAPIKey(user.id)}
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                  >
                    Generate Key
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Dashboard;
```

### Step 5: Run Admin Panel
```bash
npm start
```

✅ **Admin Panel running at**: `http://localhost:3000`

---

## 🔗 Network Configuration Guide

### For Development (Same Machine)

**Backend URL**: `http://localhost:8080/api`

**Mobile (Android Emulator)**:
- Update to: `http://10.0.2.2:8080/api`

**Mobile (Android Device/iOS)**:
- Find your machine IP: 
  - Windows: `ipconfig` → IPv4 Address
  - Mac/Linux: `ifconfig` → inet
- Update to: `http://192.168.x.x:8080/api` (your IP)

### For Production
- Deploy backend to cloud (AWS, Heroku, etc.)
- Update all apps to use production URL
- Use HTTPS for security

---

## 🧪 Testing Credentials

After registering via the app:
```
Email: test@example.com
Password: password123
Username: testuser
```

---

## 📊 API Endpoints Quick Reference

| Method | Endpoint | Purpose |
|--------|----------|----------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login user |
| POST | `/server/start` | Start server access |
| GET | `/server/active` | Get active sessions |
| POST | `/server/{id}/stop` | Stop server access |
| POST | `/admin/generate-key` | Generate API key (Admin) |
| GET | `/admin/users/{id}/keys` | Get user keys (Admin) |

---

## 🐛 Troubleshooting

### Backend Issues
```bash
# Clean and rebuild
mvn clean install

# Check if port 8080 is in use
lsof -i :8080

# Kill process on port 8080
kill -9 <PID>
```

### Android Issues
```bash
# Clear Android cache
./gradlew clean

# Rebuild
./gradlew build

# API not connecting? Check:
# 1. Backend is running
# 2. Correct IP in ApiConfig.kt
# 3. Firewall allows port 8080
```

### iOS Issues
```bash
# Pod issues
pod deintegrate
pod install

# Check API configuration in APIConfig.swift
# Verify network connectivity
```

### Admin Panel Issues
```bash
# Clear npm cache
npm cache clean --force

# Reinstall packages
rm -rf node_modules package-lock.json
npm install

# Check environment variables in .env
```

---

## ✅ Verification Checklist

- [ ] Backend running on `http://localhost:8080/api`
- [ ] Database created and tables initialized
- [ ] Android app installed and runs
- [ ] iOS app installed and runs
- [ ] Can register new user
- [ ] Can login with credentials
- [ ] Can start server access
- [ ] Timer displays correctly
- [ ] Admin panel loads users
- [ ] Can generate API keys

---

## 📞 Support & Next Steps

If you encounter issues:
1. Check logs in backend console
2. Use Postman to test API endpoints
3. Verify network connectivity
4. Check Android Studio/Xcode logs
5. Ensure all services are running

For production deployment, consider:
- Adding authentication middleware
- Implementing rate limiting
- Setting up SSL/HTTPS
- Configuring CORS properly
- Adding database backups

