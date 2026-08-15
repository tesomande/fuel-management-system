import React, { useState, useEffect } from 'react';
import StockService from '../services/StockService';
import VehicleService from '../services/VehicleService';
import TransactionService from '../services/TransactionService';

function Dashboard() {
  const [stocks, setStocks] = useState([]);
  const [vehicles, setVehicles] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [statusMap, setStatusMap] = useState({ 
    stock: '🔄 Connecting', 
    vehicle: '🔄 Connecting', 
    transaction: '🔄 Connecting' 
  });

  useEffect(() => {
    async function loadDashboardData() {
      // 1. Fetch Stocks Safely
      try {
        const res = await StockService.getAllStocks();
        setStocks(Array.isArray(res.data) ? res.data : []);
        setStatusMap(prev => ({ ...prev, stock: 'Connected' }));
      } catch (err) {
        console.error("Stock fetch failed:", err.response?.status || err.message);
        setStatusMap(prev => ({ ...prev, stock: 'Route Offline' }));
      }

      // 2. Fetch Vehicles Safely
      try {
        const res = await VehicleService.getAllVehicles();
        setVehicles(Array.isArray(res.data) ? res.data : []);
        setStatusMap(prev => ({ ...prev, vehicle: 'Connected' }));
      } catch (err) {
        console.error("Vehicle fetch failed:", err.response?.status || err.message);
        setStatusMap(prev => ({ ...prev, vehicle: 'Route Offline' }));
      }

      // 3. Fetch Transactions Safely
      try {
        const res = await TransactionService.getAllTransactions();
        setTransactions(Array.isArray(res.data) ? res.data : []);
        setStatusMap(prev => ({ ...prev, transaction: 'Connected' }));
      } catch (err) {
        console.error("Transaction fetch failed:", err.response?.status || err.message);
        setStatusMap(prev => ({ ...prev, transaction: 'Route Offline' }));
      }

      // Drop loading state
      setLoading(false);
    }

    loadDashboardData();
  }, []);

  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center p-16 space-y-4">
        <div className="w-12 h-12 border-4 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
        <p className="text-gray-600 font-semibold animate-pulse">Syncing AAWSA Operations Platform...</p>
      </div>
    );
  }

  // Safe numerical aggregation
  const totalFuelOnHand = (stocks || []).reduce(
    (sum, item) => sum + (Number(item?.quantityLiters) || 0), 
    0
  );

  return (
    <div className="space-y-8 max-w-7xl mx-auto p-4 animate-fadeIn">
      
      {/* Top Welcome Control Panel Banner with Logo */}
      <div className="bg-gradient-to-r from-blue-800 to-indigo-900 p-8 rounded-2xl text-white shadow-md flex flex-col md:flex-row md:items-center md:justify-between gap-6">
        <div className="space-y-2 overflow-hidden w-full relative">
          <div className="flex items-center">
            <h2 className="text-3xl font-black tracking-tight inline-block">
              Welcome To AAWSA Fuel & Lubricant Mgt System Dashboard
            </h2>
          </div>
          <span className="mx-4 text-blue-400 text-2xl inline-block">
            <p className="text-blue-100/80 text-xl font-bold inline-block tracking-wide flex items-center justify-center">
              •Water is Life!!!        
            </p>
          </span>
        </div>                
        
        {/* AAWSA LOGO BADGE CUTOUT */}
        <div className="flex-shrink-0 bg-white p-2 rounded-xl shadow-inner self-start md:self-center">
          <img 
            src="/aawsa-logo.png" 
            alt="AAWSA Logo" 
            className="h-16 w-auto object-contain mix-blend-multiply"
            onError={(e) => { e.target.style.display = 'none'; }}
          />
        </div>
      </div>

      {/* SYSTEM PIPELINE HEALTH LOG */}
      <div className="bg-white border border-gray-200 p-4 rounded-xl shadow-sm text-xs space-y-2">
        <h4 className="font-bold text-gray-500 uppercase tracking-wider">📡 System Pipeline Health Log:</h4>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 font-mono">
          <div className="flex justify-between items-center bg-gray-50 p-2 rounded border">
            <span className="text-gray-600 font-medium">/api/stock/all:</span>
            <span className={`px-2 py-0.5 rounded font-bold ${statusMap.stock === 'Connected' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>{statusMap.stock}</span>
          </div>
          <div className="flex justify-between items-center bg-gray-50 p-2 rounded border">
            <span className="text-gray-600 font-medium">/api/vehicles/all:</span>
            <span className={`px-2 py-0.5 rounded font-bold ${statusMap.vehicle === 'Connected' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>{statusMap.vehicle}</span>
          </div>
          <div className="flex justify-between items-center bg-gray-50 p-2 rounded border">
            <span className="text-gray-600 font-medium">/api/fuel/all:</span>
            <span className={`px-2 py-0.5 rounded font-bold ${statusMap.transaction === 'Connected' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>{statusMap.transaction}</span>
          </div>
        </div>
      </div>

      {/* KPI CARDS */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex items-center justify-between hover:shadow-md transition-shadow">
          <div>
            <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Active Fleet Profiles</p>
            <h3 className="text-3xl font-black text-gray-800 mt-1">
              {(vehicles?.length ?? 0).toLocaleString()}
            </h3>
          </div>
          <div className="text-2xl p-3 bg-blue-50 rounded-xl text-blue-600">🚗</div>
        </div>

        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex items-center justify-between hover:shadow-md transition-shadow">
          <div>
            <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Aggregate Fuel Reserves</p>
            <h3 className="text-3xl font-black text-gray-800 mt-1">
              {(totalFuelOnHand ?? 0).toLocaleString()} L
            </h3>
          </div>
          <div className="text-2xl p-3 bg-emerald-50 rounded-xl text-emerald-600">🛢️</div>
        </div>

        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex items-center justify-between hover:shadow-md transition-shadow">
          <div>
            <p className="text-xs font-bold text-gray-400 uppercase tracking-wider">Transactions Completed</p>
            <h3 className="text-3xl font-black text-gray-800 mt-1">
              {(transactions?.length ?? 0).toLocaleString()}
            </h3>
          </div>
          <div className="text-2xl p-3 bg-purple-50 rounded-xl text-purple-600">🔄</div>
        </div>
      </div>

      {/* RESERVOIR PROGRESS TRACKER */}
      <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <h3 className="text-lg font-bold text-gray-800 mb-6 border-b border-gray-100 pb-2">🛢️ Reservoir Capacity Tracker</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {(stocks || []).map((stock) => {
            const maxCapacity = 20000;
            const currentVolume = Number(stock?.quantityLiters) || 0;
            const fillingPercentage = Math.min(Math.round((currentVolume / maxCapacity) * 100), 100);
            const isDiesel = stock?.fuelType?.toLowerCase().includes('diesel');

            return (
              <div key={stock?.id || Math.random()} className="p-4 bg-gray-50 rounded-xl border border-gray-100 space-y-3">
                <div className="flex justify-between items-center font-bold">
                  <span className="text-gray-700 uppercase tracking-tight text-base">{stock?.fuelType || 'Unknown'}</span>
                  <span className="text-sm text-gray-500 font-semibold">{currentVolume.toLocaleString()} / {maxCapacity.toLocaleString()} L</span>
                </div>
                <div className="w-full bg-gray-200 rounded-full h-3 overflow-hidden border">
                  <div 
                    className={`h-full rounded-full transition-all duration-1000 ${isDiesel ? 'bg-amber-500' : 'bg-blue-600'}`}
                    style={{ width: `${fillingPercentage}%` }}
                  ></div>
                </div>
              </div>
            );
          })}
        </div>
      </div>

    </div>
  );
}

export default Dashboard;
