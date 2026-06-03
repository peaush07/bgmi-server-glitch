import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Users.css';

const Users = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [showModal, setShowModal] = useState(false);

  const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
  const token = localStorage.getItem('adminToken');

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      // Placeholder - implement when backend endpoint available
      setUsers([]);
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
          keyName: `API_KEY_${Date.now()}`,
          season: 'Season 1',
          expirationDays: 30,
        },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert(`API Key Generated: ${response.data.keyValue}`);
      fetchUsers();
    } catch (error) {
      alert('Error generating API key: ' + error.message);
    }
  };

  return (
    <div className="users-container">
      <h1>User Management</h1>

      {loading ? (
        <p>Loading users...</p>
      ) : (
        <div className="users-table">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {users.length === 0 ? (
                <tr>
                  <td colSpan="6" className="no-data">
                    No users found
                  </td>
                </tr>
              ) : (
                users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>{user.role}</td>
                    <td>
                      <span className={`status ${user.active ? 'active' : 'inactive'}`}>
                        {user.active ? 'Active' : 'Inactive'}
                      </span>
                    </td>
                    <td>
                      <button
                        className="btn-primary"
                        onClick={() => generateAPIKey(user.id)}
                      >
                        Generate Key
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default Users;
