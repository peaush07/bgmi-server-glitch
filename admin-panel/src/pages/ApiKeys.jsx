import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ApiKeys.css';

const ApiKeys = () => {
  const [keys, setKeys] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedUser, setSelectedUser] = useState('');

  const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
  const token = localStorage.getItem('adminToken');

  useEffect(() => {
    if (selectedUser) {
      fetchUserKeys();
    }
  }, [selectedUser]);

  const fetchUserKeys = async () => {
    try {
      setLoading(true);
      const response = await axios.get(
        `${API_URL}/admin/users/${selectedUser}/keys`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setKeys(response.data);
    } catch (error) {
      console.error('Error fetching keys:', error);
    } finally {
      setLoading(false);
    }
  };

  const revokeKey = async (keyId) => {
    if (window.confirm('Are you sure you want to revoke this key?')) {
      try {
        await axios.delete(`${API_URL}/admin/keys/${keyId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        alert('Key revoked successfully');
        fetchUserKeys();
      } catch (error) {
        alert('Error revoking key: ' + error.message);
      }
    }
  };

  return (
    <div className="apikeys-container">
      <h1>API Keys Management</h1>

      <div className="filter-section">
        <input
          type="text"
          placeholder="Enter User ID"
          value={selectedUser}
          onChange={(e) => setSelectedUser(e.target.value)}
          className="filter-input"
        />
      </div>

      {loading ? (
        <p>Loading API keys...</p>
      ) : (
        <div className="keys-table">
          <table>
            <thead>
              <tr>
                <th>Key ID</th>
                <th>Key Value</th>
                <th>Season</th>
                <th>Expires At</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {keys.length === 0 ? (
                <tr>
                  <td colSpan="6" className="no-data">
                    No API keys found
                  </td>
                </tr>
              ) : (
                keys.map((key) => (
                  <tr key={key.id}>
                    <td>{key.id}</td>
                    <td className="key-value">{key.keyValue}</td>
                    <td>{key.season}</td>
                    <td>{new Date(key.expiresAt).toLocaleDateString()}</td>
                    <td>
                      <span className={`status ${key.active ? 'active' : 'inactive'}`}>
                        {key.active ? 'Active' : 'Revoked'}
                      </span>
                    </td>
                    <td>
                      <button
                        className="btn-danger"
                        onClick={() => revokeKey(key.id)}
                      >
                        Revoke
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

export default ApiKeys;
