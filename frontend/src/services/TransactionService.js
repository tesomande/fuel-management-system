import axios from 'axios';

const API_BASE_URL = "http://localhost:7076/api/fuel";

class TransactionService {
    
    /**
     * Fetch paginated transactions from backend.
     * @param {number} page - Current page index (0-indexed)
     * @param {number} size - Number of items per page
     */
    getAllTransactions(page = 0, size = 5) {
        const token = localStorage.getItem("token");

        return axios.get(`${API_BASE_URL}/all`, {
            params: {
                page: page,
                size: size
            },
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
    }
}

export default new TransactionService();
