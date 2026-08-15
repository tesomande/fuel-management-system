import React, { useState, useEffect } from 'react';
import StockService from '../services/StockService';

function StockManager() {
    const [stocks, setStocks] = useState([]);
    const [loading, setLoading] = useState(true);
    
    // 1. Added missing state declarations for pagination
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [pageSize] = useState(5);

    // 2. Fetch data when component mounts OR when page changes
    useEffect(() => {
        fetchStocks(page, pageSize);
    }, [page, pageSize]);

    const fetchStocks = (currentPage, size) => {
        setLoading(true);
        StockService.getAllStocks(currentPage, size)
            .then(res => {
                // Extract array from Spring Page object or fallback to direct array
                const dataList = res.data.content || res.data || [];
                setStocks(dataList);
                setTotalPages(res.data.totalPages || 1);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching stock levels:", err);
                setStocks([]);
                setLoading(false);
            });
    };

    if (loading) return <div className="p-6 text-gray-500 animate-pulse">Loading stock reserves...</div>;

    return (
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-6xl mx-auto my-4">
            <div className="mb-6">
                <h2 className="text-xl font-bold text-gray-800">Fuel Stock Management</h2>
                <p className="text-xs text-gray-400">Monitor available fuel inventory volumes inside main reservoirs.</p>
            </div>

            <div className="overflow-x-auto">
                <table className="w-full text-left border-collapse">
                    <thead>
                        <tr className="border-b border-gray-200 text-xs font-semibold text-gray-400 uppercase tracking-wider bg-gray-50/70">
                            <th className="py-3 px-4">Fuel ID</th>
                            <th className="py-3 px-4">Fuel Type</th>
                            <th className="py-3 px-4 text-right">Remaining Balance</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-100 text-sm font-medium text-gray-700">
                        {stocks.length === 0 ? (
                            <tr>
                                <td colSpan="3" className="text-center py-8 text-gray-400 italic">
                                    No fuel stock records found.
                                </td>
                            </tr>
                        ) : (
                            stocks.map(stock => (
                                <tr key={stock.id} className="hover:bg-gray-50 transition-colors">
                                    <td className="py-3 px-4 text-gray-400">#{stock.id}</td>
                                    <td className="py-3 px-4 uppercase font-black text-gray-900 tracking-tight">{stock.fuelType}</td>
                                    <td className="py-3 px-4 text-right text-blue-600 font-black text-base">
                                        {(stock.quantityLiters || 0).toLocaleString()} Liters
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination Controls */}
            <div className="flex justify-center items-center gap-4 mt-6">
                <button
                    disabled={page === 0}
                    onClick={() => setPage(page - 1)}
                    className="px-4 py-2 bg-blue-600 text-white rounded disabled:bg-gray-300 transition-colors"
                >
                    Previous
                </button>

                <span className="text-sm font-medium text-gray-700">
                    Page {page + 1} of {totalPages}
                </span>

                <button
                    disabled={page >= totalPages - 1}
                    onClick={() => setPage(page + 1)}
                    className="px-4 py-2 bg-blue-600 text-white rounded disabled:bg-gray-300 transition-colors"
                >
                    Next
                </button>
            </div>
        </div>
    );
}

export default StockManager;
