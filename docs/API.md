# API Documentation

## Base URL
```
http://your-server:8080/api
```

## Authentication

All authenticated endpoints require a JWT token in the `Authorization` header:
```
Authorization: Bearer <token>
```

## Endpoints

### Authentication

#### Register User
```
POST /auth/register
Content-Type: application/json

{
  "username": "user123",
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "token": "jwt-token",
  "message": "User registered successfully",
  "userId": 1,
  "username": "user123"
}
```

#### Login
```
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response:
{
  "token": "jwt-token",
  "message": "Login successful",
  "userId": 1,
  "username": "user123"
}
```

### Server Access

#### Start Server Access
```
POST /server/start
Authorization: Bearer <token>
Content-Type: application/json

{
  "serverName": "server1",
  "season": "season1",
  "accessType": "FREE_TRIAL"
}

Response:
{
  "id": 1,
  "serverName": "server1",
  "remainingTimeMillis": 300000,
  "expiresAt": "2024-01-01T12:05:00",
  "status": "ACTIVE",
  "message": "Server access started"
}
```

#### Get Active Access
```
GET /server/active
Authorization: Bearer <token>

Response:
[
  {
    "id": 1,
    "serverName": "server1",
    "remainingTimeMillis": 250000,
    "expiresAt": "2024-01-01T12:05:00",
    "status": "ACTIVE"
  }
]
```

#### Stop Server Access
```
POST /server/{accessId}/stop
Authorization: Bearer <token>

Response:
{
  "message": "Server access stopped",
  "status": "SUCCESS"
}
```

### Admin Endpoints

#### Generate API Key
```
POST /admin/generate-key
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "userId": 1,
  "keyName": "key1",
  "season": "season1",
  "expirationDays": 30
}

Response:
{
  "id": 1,
  "keyValue": "bgmi_xyz123abc",
  "keyName": "key1",
  "season": "season1",
  "expiresAt": "2024-01-31T12:00:00",
  "active": true
}
```

#### Get User API Keys
```
GET /admin/users/{userId}/keys
Authorization: Bearer <admin-token>

Response:
[
  {
    "id": 1,
    "keyValue": "bgmi_xyz123abc",
    "keyName": "key1",
    "season": "season1",
    "expiresAt": "2024-01-31T12:00:00",
    "active": true
  }
]
```

## Error Responses

```json
{
  "error": "Error message",
  "status": 400,
  "timestamp": "2024-01-01T12:00:00"
}
```

## Status Codes

- `200` - Success
- `201` - Created
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `500` - Internal Server Error
