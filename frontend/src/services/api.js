// src/services/api.js
import axios from 'axios';

// Basic Auth header for 'user:123'
const authHeader = 'Basic ' + btoa('user:123');

const API_BASE_URL = 'http://localhost:7076/api';

export const fetchDashboardData = async () => {
  try {
    const [vehiclesRes, stockRes, fuelRes] = await Promise.all([
      axios.get(`${API_BASE_URL}/vehicles/all`, { headers: { 'Authorization': authHeader } }),
      axios.get(`${API_BASE_URL}/stock/all`, { headers: { 'Authorization': authHeader } }),
      axios.get(`${API_BASE_URL}/fuel/all`, { headers: { 'Authorization': authHeader } })
    ]);

    return {
      vehicles: vehiclesRes.data,
      stock: stockRes.data,
      fuel: fuelRes.data
    };
  } catch (error) {
    console.error("Fetch error:", error);
    throw error;
  }
};
