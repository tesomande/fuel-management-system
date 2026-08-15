import axios from 'axios';

// Target port 7075
const API_BASE_URL = 'http://localhost:7076/api';

// Basic Auth credentials for 'user:123'
const authHeader = 'Basic ' + btoa('user:123');

const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json'
    },
    withCredentials: true
});

class DashboardService {
    
    // Primary summary endpoint with auto-fallback calculation
    async getSummary() {
        try {
            const response = await axiosInstance.get('/dashboard/summary');
            let data = response.data || {};

            // If nested inside a payload wrapper (e.g. { data: { ... } })
            if (data.data && typeof data.data === 'object') {
                data = data.data;
            }

            // Extract values supporting common Java DTO naming conventions
            let vehicles = data.totalVehicles ?? data.vehicleCount ?? data.totalVehicleCount ?? data.vehiclesCount ?? 0;
            let transactions = data.totalTransactions ?? data.transactionCount ?? data.totalTransactionCount ?? data.fuelLogsCount ?? 0;
            let fuelInStock = data.totalFuelInStock ?? data.totalStock ?? data.fuelInStock ?? 0;
            let fuelTypes = data.totalFuelTypes ?? data.fuelTypeCount ?? 0;
            let lowStock = data.lowStockCount ?? data.lowStockAlerts ?? 0;

            // FALLBACK: If summary endpoint returned 0 for vehicles or transactions, fetch true counts directly
            if (vehicles === 0 || transactions === 0) {
                const [vRes, tRes, sRes] = await Promise.allSettled([
                    axiosInstance.get('/vehicles/all'),
                    axiosInstance.get('/fuel/all'),
                    axiosInstance.get('/stock/all')
                ]);

                if (vehicles === 0 && vRes.status === 'fulfilled') {
                    const vList = Array.isArray(vRes.value.data) ? vRes.value.data : (vRes.value.data?.data || []);
                    vehicles = vList.length;
                }

                if (transactions === 0 && tRes.status === 'fulfilled') {
                    const tList = Array.isArray(tRes.value.data) ? tRes.value.data : (tRes.value.data?.data || []);
                    transactions = tList.length;
                }

                if (fuelInStock === 0 && sRes.status === 'fulfilled') {
                    const sList = Array.isArray(sRes.value.data) ? sRes.value.data : (sRes.value.data?.data || []);
                    fuelInStock = sList.reduce((sum, item) => sum + (Number(item?.quantityLiters) || 0), 0);
                    fuelTypes = sList.length;
                }
            }

            return {
                data: {
                    totalVehicles: vehicles,
                    totalTransactions: transactions,
                    totalFuelInStock: fuelInStock,
                    totalFuelTypes: fuelTypes,
                    lowStockCount: lowStock
                }
            };
        } catch (error) {
            console.warn("Summary endpoint failed, calculating metrics from individual endpoints...", error);

            // Complete fallback using individual API routes
            const [vRes, tRes, sRes] = await Promise.allSettled([
                axiosInstance.get('/vehicles/all'),
                axiosInstance.get('/fuel/all'),
                axiosInstance.get('/stock/all')
            ]);

            const vList = vRes.status === 'fulfilled' && Array.isArray(vRes.value.data) ? vRes.value.data : [];
            const tList = tRes.status === 'fulfilled' && Array.isArray(tRes.value.data) ? tRes.value.data : [];
            const sList = sRes.status === 'fulfilled' && Array.isArray(sRes.value.data) ? sRes.value.data : [];

            const totalFuel = sList.reduce((sum, item) => sum + (Number(item?.quantityLiters) || 0), 0);

            return {
                data: {
                    totalVehicles: vList.length,
                    totalTransactions: tList.length,
                    totalFuelInStock: totalFuel,
                    totalFuelTypes: sList.length,
                    lowStockCount: sList.filter(s => (s.quantityLiters || 0) < 1000).length
                }
            };
        }
    }

    // Health check routes
    checkStockStatus() {
        return axiosInstance.get('/stock/all');
    }

    checkVehicleStatus() {
        return axiosInstance.get('/vehicles/all');
    }

    checkFuelStatus() {
        return axiosInstance.get('/fuel/all');
    }
}

export default new DashboardService();
