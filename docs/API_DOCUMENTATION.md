# BGMI Server Glitch - API Documentation

## Base URL
```
http://your-api-domain.com/api
```

## Authentication Endpoints

### 1. Google Sign-In
**POST** `/auth/google`

Request:
```json
{
  "googleToken": "google-oauth-token"
}
```

Response:
```json
{
  "success": true,
  "token": "jwt-token",
  "email": "user@gmail.com",
  "profile": { ... }
}
```

---

## Key Management Endpoints

### 2. Get User Keys
**GET** `/keys/my-keys`

Headers:
```
Authorization: Bearer {jwt-token}
Content-Type: application/json
```

Response:
```json
{
  "success": true,
  "keys": [
    {
      "keyId": "KEY_xxx",
      "serverId": "server-1",
      "season": "Season 5",
      "expiresAt": "2024-06-05T14:30:00Z",
      "timeRemaining": 3600,
      "isExpired": false,
      "isUsed": false,
      "createdAt": "2024-06-04T12:30:00Z"
    }
  ]
}
```

### 3. Validate Key
**POST** `/keys/validate`

Request:
```json
{
  "keyId": "KEY_xxx"
}
```

Response:
```json
{
  "success": true,
  "message": "Key is valid",
  "key": {
    "keyId": "KEY_xxx",
    "serverId": "server-1",
    "season": "Season 5",
    "timeRemaining": 3600
  }
}
```

### 4. Add Key (Admin)
**POST** `/keys/add`

Headers:
```
Authorization: Bearer {jwt-token}
Content-Type: application/json
```

Request:
```json
{
  "serverId": "server-1",
  "season": "Season 5",
  "durationMinutes": 1440
}
```

Response:
```json
{
  "success": true,
  "key": {
    "keyId": "KEY_xxx",
    "expiresAt": "2024-06-05T12:30:00Z",
    "durationMinutes": 1440,
    "serverId": "server-1",
    "season": "Season 5"
  }
}
```

---

## Server Endpoints

### 5. Get Available Servers
**GET** `/servers`

Response:
```json
{
  "success": true,
  "servers": [
    {
      "serverId": "server-1",
      "name": "BGMI Server - India",
      "season": "Season 5",
      "status": "online",
      "maxPlayers": 100,
      "currentPlayers": 45,
      "region": "IN"
    }
  ]
}
```

### 6. Get Free Trial (5 Minutes)
**POST** `/servers/free-trial`

Headers:
```
Authorization: Bearer {jwt-token}
Content-Type: application/json
```

Request:
```json
{
  "serverId": "server-1"
}
```

Response:
```json
{
  "success": true,
  "trial": {
    "keyId": "TRIAL_xxx",
    "serverId": "server-1",
    "expiresAt": "2024-06-04T12:35:00Z",
    "durationMinutes": 5,
    "message": "Free 5-minute trial granted"
  }
}
```

### 7. Select Server
**POST** `/servers/select`

Headers:
```
Authorization: Bearer {jwt-token}
Content-Type: application/json
```

Request:
```json
{
  "serverId": "server-1",
  "keyId": "KEY_xxx"
}
```

Response:
```json
{
  "success": true,
  "message": "Server selected successfully",
  "connection": {
    "serverId": "server-1",
    "serverIP": "192.168.1.1",
    "port": 9000,
    "keyId": "KEY_xxx",
    "session": "SESSION_xxx"
  }
}
```

---

## Timer Endpoints

### 8. Get Timer Data
**GET** `/timer/{keyId}`

Response:
```json
{
  "success": true,
  "keyId": "KEY_xxx",
  "expiresAt": "2024-06-05T14:30:00Z",
  "timeRemaining": 3600,
  "isExpired": false,
  "isUsed": false,
  "percentageRemaining": 50.0
}
```

---

## Season Endpoints

### 9. Get Active Seasons
**GET** `/seasons`

Response:
```json
{
  "success": true,
  "seasons": [
    {
      "id": "SEASON_xxx",
      "name": "Season 5",
      "startDate": "2024-06-01T00:00:00Z",
      "endDate": "2024-06-30T23:59:59Z",
      "createdAt": "2024-05-15T10:00:00Z"
    }
  ]
}
```

### 10. Create Season (Admin)
**POST** `/seasons/create`

Headers:
```
Authorization: Bearer {jwt-token}
Content-Type: application/json
```

Request:
```json
{
  "name": "Season 6",
  "startDate": "2024-07-01T00:00:00Z",
  "endDate": "2024-07-31T23:59:59Z"
}
```

Response:
```json
{
  "success": true,
  "season": {
    "id": "SEASON_xxx",
    "name": "Season 6",
    "startDate": "2024-07-01T00:00:00Z",
    "endDate": "2024-07-31T23:59:59Z",
    "createdAt": "2024-06-04T12:00:00Z"
  }
}
```

---

## Error Responses

### 401 Unauthorized
```json
{
  "error": "Invalid token" or "No token provided"
}
```

### 404 Not Found
```json
{
  "error": "Key not found" or "Server not found"
}
```

### 400 Bad Request
```json
{
  "error": "Key already used" or "Key expired"
}
```

### 500 Server Error
```json
{
  "error": "Internal server error"
}
```

---

## Rate Limiting

- **Default**: 100 requests per minute
- **Headers**: `X-RateLimit-Limit`, `X-RateLimit-Remaining`, `X-RateLimit-Reset`

---

## Environment Variables

```env
PORT=5000
JWT_SECRET=your-secret-key
GOOGLE_CLIENT_ID=your-google-id
MONGODB_URI=mongodb://localhost/bgmi-glitch
```
