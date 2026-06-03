# Admin Panel

## Overview
Admin dashboard for managing users, generating API keys, monitoring active sessions, and managing servers.

## Features

- User management
- API key generation & management
- Active session monitoring
- Server management
- Revenue analytics
- Real-time stats

## Tech Stack Options

1. **React + TypeScript**
2. **Vue 3 + TypeScript**
3. **Angular**

## Setup Instructions

### Using React (Recommended)

```bash
npx create-react-app admin-panel
cd admin-panel
npm install axios react-router-dom
```

### Key API Endpoints

- `POST /api/admin/generate-key` - Generate API key
- `GET /api/admin/users/{userId}/keys` - Get user keys
- `GET /api/admin/sessions` - Get active sessions

## Environment Variables

```
REACT_APP_API_URL=http://your-backend-url/api
REACT_APP_ADMIN_TOKEN=your-admin-token
```
