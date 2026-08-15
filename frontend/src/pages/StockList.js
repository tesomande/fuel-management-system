import { useEffect, useState } from "react";
import StockService from "../services/StockService";

function StockList() {

    const [stocks, setStocks] = useState([]);

    useEffect(() => {

        StockService.getAllStocks()
            .then((response) => {
                setStocks(response.data);
            })
            .catch((error) => {
                console.error(error);
            });

    }, []);

    return (
        <div className="container mt-5">

            <h2>Fuel Stock List</h2>

            <table className="table table-bordered">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fuel Type</th>
                        <th>Quantity (Liters)</th>
                    </tr>
                </thead>

                <tbody>

                    {stocks.map(stock => (

                        <tr key={stock.id}>
                            <td>{stock.id}</td>
                            <td>{stock.fuelType}</td>
                            <td>{stock.quantityLiters}</td>
                        </tr>

                    ))}

                </tbody>

            </table>

        </div>
    );

}

export default StockList;
