# Backend - Java Spring Boot Server

## Overview
Java Spring Boot backend for managing API key generation, user authentication, server access control, and admin operations.

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- MySQL 8.0 or PostgreSQL 13+

### Installation

1. Create `pom.xml` with dependencies
2. Configure `application.properties` or `application.yml`
3. Run migrations
4. Start the application

## Key Endpoints

- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/admin/generate-key` - Generate API key (Admin only)
- `GET /api/user/dashboard` - User dashboard
- `POST /api/server/start` - Start server access
- `POST /api/server/stop` - Stop server access
- `GET /api/user/keys` - List user API keys

## Database Schema

See `src/main/resources/schema.sql`
