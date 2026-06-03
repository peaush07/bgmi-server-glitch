# Admin Panel - React Dashboard

## Project Structure

```
admin-panel/
├── src/
│   ├── components/
│   │   ├── Header.jsx
│   │   ├── Sidebar.jsx
│   │   └── PrivateRoute.jsx
│   ├── pages/
│   │   ├── Dashboard.jsx
│   │   ├── Users.jsx
│   │   ├── ApiKeys.jsx
│   │   ├── Sessions.jsx
│   │   ├── Login.jsx
│   │   └── Analytics.jsx
│   ├── services/
│   │   └── api.js
│   ├── App.jsx
│   └── index.css
├── .env
├── package.json
└── public/
```

## Features

- **User Management**: View all users, search, filter
- **API Key Generation**: Create and manage API keys per user
- **Session Monitoring**: View active sessions, bandwidth usage
- **Analytics**: Revenue, usage statistics
- **Admin Authentication**: Secure login

## Installation

```bash
cd admin-panel
npx create-react-app .
npm install axios react-router-dom react-icons
```

## Environment Variables

Create `.env` file:
```
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_ADMIN_TOKEN=your_token_here
```

## Running

```bash
npm start
```

Access at: `http://localhost:3000`
