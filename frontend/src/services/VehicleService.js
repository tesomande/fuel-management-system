import axios from 'axios';

const API_BASE_URL = "http://localhost:7076/api/vehicles/all";


class VehicleService {

    getAllVehicles(page = 0) {

        const token = localStorage.getItem("token");


        return axios.get(API_BASE_URL, {

            params: {
                page: page,
                size: 5
            },

            headers: {
                Authorization: `Bearer ${token}`
            }

        });

    }

}


export default new VehicleService();
