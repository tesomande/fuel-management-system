import React, { useState } from 'react';
import axios from 'axios';

// Component Imports from src/components/
//import Dashboard from './components/Dashboard';
import Dashboard from './components/Dashboard.jsx';
import VehicleManager from './components/VehicleManager';
import StockManager from './components/StockManager';
import TransactionManager from './components/TransactionManager';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');

  // State for Dashboard Summary Modal
  const [isSummaryOpen, setIsSummaryOpen] = useState(false);
  const [summaryData, setSummaryData] = useState(null);
  const [loadingSummary, setLoadingSummary] = useState(false);

  // Fetch summary data from Spring Boot endpoint
  const handleOpenSummary = async () => {
    setLoadingSummary(true);
    try {
      const response = await axios.get('http://localhost:7076/api/dashboard/summary');
      setSummaryData(response.data);
      setIsSummaryOpen(true);
    } catch (error) {
      console.error('Failed to load dashboard summary:', error);
      alert('Could not retrieve dashboard summary. Ensure your backend service is running on port 7075.');
    } finally {
      setLoadingSummary(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col font-sans">
      {/* Navigation Header Banner */}
      <nav className="bg-blue-600 text-white shadow-md">
        <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">
          
          {/* Logo & Title */}
          <div className="flex items-center space-x-4">
            <img 
              src="/aawsa-logo.png" 
              alt="AAWSA Logo" 
              className="h-16 w-auto bg-white p-1 rounded border border-blue-400"
            />
            <h1 className="text-xl font-black tracking-wide">
              AAWSA Fuel & Lubricant Management System
            </h1>
          </div>

          {/* Navigation Items */}
          <div className="flex space-x-2 items-center">
            
            <button 
              onClick={() => setActiveTab('dashboard')} 
              className={`px-4 py-2 rounded-lg text-sm font-bold transition-all ${activeTab === 'dashboard' ? 'bg-blue-800 shadow-inner' : 'hover:bg-blue-700/50'}`}
            >
              📊 Dashboard
            </button>

            <button 
              onClick={() => setActiveTab('vehicles')} 
              className={`px-4 py-2 rounded-lg text-sm font-bold transition-all ${activeTab === 'vehicles' ? 'bg-blue-800 shadow-inner' : 'hover:bg-blue-700/50'}`}
            >
              🚗 Vehicles
            </button>

            <button 
              onClick={() => setActiveTab('stock')} 
              className={`px-4 py-2 rounded-lg text-sm font-bold transition-all ${activeTab === 'stock' ? 'bg-blue-800 shadow-inner' : 'hover:bg-blue-700/50'}`}
            >
              🛢️ Fuel Stock
            </button>

            <button 
              onClick={() => setActiveTab('transactions')} 
              className={`px-4 py-2 rounded-lg text-sm font-bold transition-all ${activeTab === 'transactions' ? 'bg-blue-800 shadow-inner' : 'hover:bg-blue-700/50'}`}
            >
              🔄 Transactions
            </button>

            {/* Dashboard Summary Trigger Button - Positioned directly next to Transactions */}
            <button
              onClick={handleOpenSummary}
              disabled={loadingSummary}
              className="bg-amber-500 hover:bg-amber-600 text-white px-4 py-2 rounded-lg text-sm font-bold transition-all shadow border border-amber-400"
            >
              {loadingSummary ? 'Loading...' : '📋 Dashboard Summary'}
            </button>

          </div>
        </div>
      </nav>

      {/* Main Content Area */}
      <main className="flex-grow p-6 max-w-7xl w-full mx-auto">
        {activeTab === 'dashboard' && <Dashboard />}
        {activeTab === 'vehicles' && <VehicleManager />}
        {activeTab === 'stock' && <StockManager />}
        {activeTab === 'transactions' && <TransactionManager />}
      </main>

      {/* Quick Summary Modal Overlay */}
      {isSummaryOpen && summaryData && (
        <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-xl max-w-md w-full p-6 shadow-2xl text-gray-800">
            <div className="flex justify-between items-center border-b pb-3 mb-4">
              <h2 className="text-xl font-bold text-blue-900">Dashboard Summary Overview</h2>
              <button 
                onClick={() => setIsSummaryOpen(false)}
                className="text-gray-400 hover:text-gray-700 text-lg font-bold"
              >
                ✕
              </button>
            </div>

            <div className="space-y-3">
              <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                <span className="text-gray-600 font-medium">Total Vehicles</span>
                <span className="font-bold text-blue-700 text-lg">{summaryData.totalVehicles}</span>
              </div>

              <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                <span className="text-gray-600 font-medium">Total Transactions</span>
                <span className="font-bold text-blue-700 text-lg">{summaryData.totalTransactions}</span>
              </div>

              <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                <span className="text-gray-600 font-medium">Fuel Types Registered</span>
                <span className="font-bold text-blue-700 text-lg">{summaryData.totalFuelTypes}</span>
              </div>

              <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                <span className="text-gray-600 font-medium">Total Fuel in Stock</span>
                <span className="font-bold text-emerald-600 text-lg">{summaryData.totalFuelInStock?.toLocaleString()} L</span>
              </div>

              <div className="flex justify-between items-center p-3 bg-rose-50 border border-rose-200 rounded-lg">
                <span className="text-rose-700 font-medium">Low Stock Alerts</span>
                <span className="font-bold text-rose-700 text-lg">{summaryData.lowStockCount}</span>
              </div>
            </div>

            <button 
              onClick={() => setIsSummaryOpen(false)}
              className="mt-6 w-full py-2.5 bg-blue-600 hover:bg-blue-700 text-white font-bold rounded-lg transition-all"
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
