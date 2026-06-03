# BGMI Server Glitch - Server Hacking System

A comprehensive platform for server access management with API key generation, user authentication, and mobile applications.

## Project Structure

```
bgmi-server-glitch/
├── backend/                    # Java Spring Boot Backend
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── mobile/                     # Mobile Applications
│   ├── react-native/          # React Native App
│   ├── android/               # Native Android App
│   └── ios/                   # Native iOS App
├── admin-panel/               # Admin Dashboard
│   └── README.md
└── docs/                      # Documentation
    └── API.md
```

## Features

### Backend (Java Spring Boot)
- User authentication & authorization
- API key generation & management
- Server access control
- Time-based access (5 min free + premium tiers)
- Keys & Seasons system
- Admin panel API endpoints

### Mobile App (User Side)
- Server name input
- Start/Stop server access
- Real-time timer (5 minutes free)
- Purchase additional time/keys
- Season selection
- User dashboard

### Admin Panel
- Generate API keys for users
- Manage servers
- Monitor active sessions
- User management
- Revenue analytics

## Tech Stack

- **Backend**: Java 17+, Spring Boot 3.x, Spring Security, JPA/Hibernate
- **Database**: PostgreSQL / MySQL
- **Mobile**: React Native, Flutter, or Native Android/iOS
- **Frontend (Admin)**: React, Vue, or Angular

## Getting Started

See individual README files in each directory for setup instructions.

## API Documentation

See `docs/API.md` for detailed API endpoints.
