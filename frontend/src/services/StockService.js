import axios from "axios";

const API_URL = "http://localhost:7076/api/stock";

class StockService {
    /**
     * Fetch paginated stocks from backend.
     * @param {number} page - Current page index (0-indexed)
     * @param {number} size - Number of items per page
     */
    getAllStocks(page = 0, size = 5) {
        // 1. Grab the token from storage
        let token = localStorage.getItem("token");

        // 2. Clear any lingering literal string quotes if they exist
        if (token) {
            token = token.replace(/^"(.*)"$/, '$1');
        }

        // 3. Pass page, size, and Authorization header in Axios request
        return axios.get(`${API_URL}/all`, {
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

export default new StockService();
