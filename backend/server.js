// BGMI Server Glitch - Backend API Server
// Node.js/Express REST API for key validation, timer management, and server access

const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const jwt = require('jsonwebtoken');
const axios = require('axios');

dotenv.config();

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors());
app.use(express.json());

// ============= DATABASE MOCK (Replace with MongoDB/Firebase) =============
const users = {}; // { email: { profile, keys } }
const keys = {};  // { keyId: { email, server, season, expiresAt, isUsed } }
const servers = {};  // { serverId: { name, season, status } }
const seasons = []; // Array of active seasons

// ============= AUTHENTICATION =============

// Verify Google OAuth Token
async function verifyGoogleToken(token) {
  try {
    const response = await axios.get(
      `https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=${token}`
    );
    return response.data;
  } catch (error) {
    console.error('Token verification failed:', error.message);
    return null;
  }
}

// Generate JWT Token
function generateJWT(email) {
  return jwt.sign({ email }, process.env.JWT_SECRET || 'your-secret-key', {
    expiresIn: '24h'
  });
}

// Middleware: Verify JWT
function verifyToken(req, res, next) {
  const token = req.headers.authorization?.split(' ')[1];
  if (!token) return res.status(401).json({ error: 'No token provided' });

  jwt.verify(token, process.env.JWT_SECRET || 'your-secret-key', (err, decoded) => {
    if (err) return res.status(401).json({ error: 'Invalid token' });
    req.user = decoded;
    next();
  });
}

// ============= AUTHENTICATION ROUTES =============

// 1. Google Sign-In / Registration
app.post('/api/auth/google', async (req, res) => {
  const { googleToken } = req.body;

  const tokenInfo = await verifyGoogleToken(googleToken);
  if (!tokenInfo) {
    return res.status(401).json({ error: 'Invalid Google token' });
  }

  const email = tokenInfo.email;

  // Register or retrieve user
  if (!users[email]) {
    users[email] = {
      email,
      createdAt: new Date(),
      keys: [],
      totalSpent: 0
    };
  }

  const jwtToken = generateJWT(email);
  res.json({ 
    success: true, 
    token: jwtToken, 
    email,
    profile: users[email]
  });
});

// 2. Email/Password Sign-In (Optional)
app.post('/api/auth/login', (req, res) => {
  const { email, password } = req.body;

  // TODO: Implement password hashing and validation
  if (!users[email]) {
    return res.status(401).json({ error: 'User not found' });
  }

  const token = generateJWT(email);
  res.json({ success: true, token, email });
});

// ============= KEY MANAGEMENT ROUTES =============

// 1. Add Key for User
app.post('/api/keys/add', verifyToken, (req, res) => {
  const { serverId, season, durationMinutes } = req.body;
  const email = req.user.email;

  const keyId = `KEY_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`;
  const expiresAt = new Date(Date.now() + durationMinutes * 60000);

  keys[keyId] = {
    keyId,
    email,
    serverId,
    season,
    createdAt: new Date(),
    expiresAt,
    isUsed: false,
    usedAt: null
  };

  users[email].keys.push(keyId);

  res.json({
    success: true,
    key: {
      keyId,
      expiresAt,
      durationMinutes,
      serverId,
      season
    }
  });
});

// 2. Validate Key
app.post('/api/keys/validate', (req, res) => {
  const { keyId } = req.body;

  if (!keys[keyId]) {
    return res.status(404).json({ error: 'Key not found' });
  }

  const key = keys[keyId];
  const now = new Date();

  // Check if key is expired
  if (now > key.expiresAt) {
    return res.status(401).json({ error: 'Key expired' });
  }

  // Check if key was already used
  if (key.isUsed) {
    return res.status(401).json({ error: 'Key already used' });
  }

  // Mark key as used
  key.isUsed = true;
  key.usedAt = now;

  res.json({
    success: true,
    message: 'Key is valid',
    key: {
      keyId,
      serverId: key.serverId,
      season: key.season,
      timeRemaining: Math.floor((key.expiresAt - now) / 1000) // seconds
    }
  });
});

// 3. Get User Keys
app.get('/api/keys/my-keys', verifyToken, (req, res) => {
  const email = req.user.email;
  const userKeys = users[email]?.keys || [];

  const keysData = userKeys.map(keyId => {
    const key = keys[keyId];
    const now = new Date();
    const timeRemaining = Math.max(0, Math.floor((key.expiresAt - now) / 1000));
    const isExpired = now > key.expiresAt;

    return {
      keyId: key.keyId,
      serverId: key.serverId,
      season: key.season,
      expiresAt: key.expiresAt,
      timeRemaining,
      isExpired,
      isUsed: key.isUsed,
      createdAt: key.createdAt
    };
  });

  res.json({ success: true, keys: keysData });
});

// ============= SERVER ROUTES =============

// 1. Get Available Servers
app.get('/api/servers', (req, res) => {
  const serversList = Object.values(servers).map(server => ({
    serverId: server.serverId,
    name: server.name,
    season: server.season,
    status: server.status,
    maxPlayers: server.maxPlayers,
    currentPlayers: server.currentPlayers,
    region: server.region
  }));

  res.json({ success: true, servers: serversList });
});

// 2. Get Free Trial Access (5 minutes)
app.post('/api/servers/free-trial', verifyToken, (req, res) => {
  const { serverId } = req.body;
  const email = req.user.email;

  if (!servers[serverId]) {
    return res.status(404).json({ error: 'Server not found' });
  }

  const trialKeyId = `TRIAL_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`;
  const expiresAt = new Date(Date.now() + 5 * 60000); // 5 minutes

  keys[trialKeyId] = {
    keyId: trialKeyId,
    email,
    serverId,
    season: 'TRIAL',
    createdAt: new Date(),
    expiresAt,
    isUsed: false,
    usedAt: null,
    isTrial: true
  };

  res.json({
    success: true,
    trial: {
      keyId: trialKeyId,
      serverId,
      expiresAt,
      durationMinutes: 5,
      message: 'Free 5-minute trial granted'
    }
  });
});

// 3. Select Server
app.post('/api/servers/select', verifyToken, (req, res) => {
  const { serverId, keyId } = req.body;

  if (!keys[keyId]) {
    return res.status(404).json({ error: 'Key not found' });
  }

  const key = keys[keyId];
  if (key.isExpired || key.isUsed) {
    return res.status(401).json({ error: 'Key is not valid' });
  }

  res.json({
    success: true,
    message: 'Server selected successfully',
    connection: {
      serverId,
      serverIP: servers[serverId]?.ip || 'server.bgmi.local',
      port: 9000 + Math.random() * 1000,
      keyId,
      session: `SESSION_${Date.now()}`
    }
  });
});

// ============= SEASON ROUTES =============

// 1. Get Active Seasons
app.get('/api/seasons', (req, res) => {
  res.json({ success: true, seasons });
});

// 2. Create Season (Admin only)
app.post('/api/seasons/create', verifyToken, (req, res) => {
  const { name, startDate, endDate } = req.body;

  // TODO: Add admin verification

  const season = {
    id: `SEASON_${Date.now()}`,
    name,
    startDate,
    endDate,
    createdAt: new Date()
  };

  seasons.push(season);
  res.json({ success: true, season });
});

// ============= TIMER ENDPOINT =============

// 1. Get Countdown Timer Data
app.get('/api/timer/:keyId', (req, res) => {
  const { keyId } = req.params;

  if (!keys[keyId]) {
    return res.status(404).json({ error: 'Key not found' });
  }

  const key = keys[keyId];
  const now = new Date();
  const timeRemaining = Math.max(0, Math.floor((key.expiresAt - now) / 1000));
  const isExpired = now > key.expiresAt;

  res.json({
    success: true,
    keyId,
    expiresAt: key.expiresAt,
    timeRemaining,
    isExpired,
    isUsed: key.isUsed,
    percentageRemaining: (timeRemaining / ((key.expiresAt - key.createdAt) / 1000)) * 100
  });
});

// ============= HEALTH CHECK =============

app.get('/api/health', (req, res) => {
  res.json({ status: 'OK', timestamp: new Date() });
});

// ============= ERROR HANDLING =============

app.use((err, req, res, next) => {
  console.error(err.stack);
  res.status(500).json({ error: 'Internal server error' });
});

// ============= START SERVER =============

app.listen(PORT, () => {
  console.log(`🚀 BGMI Server Glitch API running on port ${PORT}`);
  console.log(`📍 Health check: http://localhost:${PORT}/api/health`);
});

module.exports = app;
