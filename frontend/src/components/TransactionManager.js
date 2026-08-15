import React, { useState, useEffect } from 'react';
import TransactionService from '../services/TransactionService';

function TransactionManager() {
    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    
    // Pagination State
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [pageSize] = useState(5); // Matches backend default

    useEffect(() => {
        fetchTransactions(currentPage, pageSize);
    }, [currentPage, pageSize]);

    const fetchTransactions = (page, size) => {
        setLoading(true);
        TransactionService.getAllTransactions(page, size)
            .then(res => {
                // Spring Boot Page object wraps array in 'content'
                // Fallback to res.data if endpoint isn't paginated yet
                const dataList = res.data.content || res.data || [];
                setTransactions(dataList);
                setTotalPages(res.data.totalPages || 1);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error fetching transactions:", err);
                setTransactions([]);
                setLoading(false);
            });
    };

    const handlePrevPage = () => {
        if (currentPage > 0) setCurrentPage(prev => prev - 1);
    };

    const handleNextPage = () => {
        if (currentPage < totalPages - 1) setCurrentPage(prev => prev + 1);
    };

    if (loading) return <div className="p-6 text-gray-500 animate-pulse">Loading distribution ledgers...</div>;

    return (
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-6xl mx-auto my-4">
            <div className="mb-6">
                <h2 className="text-xl font-bold text-gray-800">Dispensation Transaction Log</h2>
                <p className="text-xs text-gray-400">Historical records of active stock deductions across AAWSA fleets.</p>
            </div>

            <div className="overflow-x-auto">
                <table className="w-full text-left border-collapse">
                    <thead>
                        <tr className="border-b border-gray-200 text-xs font-semibold text-gray-400 uppercase tracking-wider bg-gray-50/70">
                            <th className="py-3 px-4">TX ID</th>
                            <th className="py-3 px-4">Driver Name</th>
                            <th className="py-3 px-4">Fuel Type</th>
                            <th className="py-3 px-4">Date Logged</th>
                            <th className="py-3 px-4 text-right">Volume Issued</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-100 text-sm text-gray-700">
                        {transactions.length === 0 ? (
                            <tr>
                                <td colSpan="5" className="text-center py-8 text-gray-400 italic">
                                    No transaction logs returned. Check backend connection to /api/fuel/all.
                                </td>
                            </tr>
                        ) : (
                            transactions.map(tx => {
                                const fuel = tx.fuelType || tx.fuel_type || 'N/A';
                                const amount = tx.liters || tx.quantityDispensed || 0;
                                const date = tx.transactionDate || tx.transaction_date || 'N/A';
                                const driver = tx.driverName || tx.driver_name || 'System App';

                                return (
                                    <tr key={tx.id} className="hover:bg-gray-50/80 transition-colors">
                                        <td className="py-3 px-4 text-gray-400 font-medium">#{tx.id}</td>
                                        <td className="py-3 px-4 font-bold text-gray-800">{driver}</td>
                                        <td className="py-3 px-4 uppercase text-xs font-extrabold text-gray-500 tracking-wider">
                                            {fuel}
                                        </td>
                                        <td className="py-3 px-4 text-xs text-gray-400 font-mono">
                                            {date !== 'N/A' ? new Date(date).toLocaleString() : 'N/A'}
                                        </td>
                                        <td className="py-3 px-4 text-right font-black text-emerald-600">
                                            {amount} Liters
                                        </td>
                                    </tr>
                                );
                            })
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination UI Controls */}
            <div className="flex items-center justify-between border-t border-gray-100 pt-4 mt-4">
                <span className="text-xs text-gray-500">
                    Page <span className="font-semibold text-gray-800">{currentPage + 1}</span> of{' '}
                    <span className="font-semibold text-gray-800">{totalPages}</span>
                </span>
                <div className="inline-flex gap-2">
                    <button
                        onClick={handlePrevPage}
                        disabled={currentPage === 0}
                        className="px-3 py-1.5 text-xs font-medium text-gray-600 bg-gray-100 rounded-lg hover:bg-gray-200 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                    >
                        Previous
                    </button>
                    <button
                        onClick={handleNextPage}
                        disabled={currentPage >= totalPages - 1}
                        className="px-3 py-1.5 text-xs font-medium text-gray-600 bg-gray-100 rounded-lg hover:bg-gray-200 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                    >
                        Next
                    </button>
                </div>
            </div>
        </div>
    );
}

export default TransactionManager;
