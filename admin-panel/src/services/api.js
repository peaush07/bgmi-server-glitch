import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('adminToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authService = {
  login: (email, password) => api.post('/auth/login', { email, password }),
  logout: () => {
    localStorage.removeItem('adminToken');
  },
};

export const userService = {
  getUsers: () => api.get('/admin/users'),
  getUserById: (id) => api.get(`/admin/users/${id}`),
  searchUsers: (query) => api.get(`/admin/users/search?q=${query}`),
};

export const apiKeyService = {
  generateKey: (userId, keyName, season, expirationDays) =>
    api.post('/admin/generate-key', {
      userId,
      keyName,
      season,
      expirationDays,
    }),
  getUserKeys: (userId) => api.get(`/admin/users/${userId}/keys`),
  revokeKey: (keyId) => api.delete(`/admin/keys/${keyId}`),
};

export const sessionService = {
  getActiveSessions: () => api.get('/admin/sessions'),
  getSessionStats: () => api.get('/admin/sessions/stats'),
};

export default api;
