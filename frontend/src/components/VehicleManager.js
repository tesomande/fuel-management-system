import React, { useState, useEffect } from 'react';
import VehicleService from '../services/VehicleService';

function VehicleManager() {

    const [vehicles, setVehicles] = useState([]);
    const [loading, setLoading] = useState(true);

    // Pagination states
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);


    useEffect(() => {

        setLoading(true);

        VehicleService.getAllVehicles(page)

            .then(res => {

                // Spring Boot Page response
                setVehicles(res.data.content);

                setTotalPages(res.data.totalPages);

                setLoading(false);

            })

            .catch(err => {

                console.error("Error fetching vehicles:", err);

                setLoading(false);

            });

    }, [page]);



    if (loading)
        return (
            <div className="p-6 text-gray-500 animate-pulse">
                Loading official fleet data...
            </div>
        );



    return (

        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 max-w-6xl mx-auto my-4">


            <div className="mb-6">

                <h2 className="text-xl font-bold text-gray-800">
                    Vehicle Records Management
                </h2>

                <p className="text-xs text-gray-400">
                    Registered assets authorized to receive fuel allocations.
                </p>

            </div>



            <div className="overflow-x-auto">

                <table className="w-full text-left border-collapse">

                    <thead>

                        <tr className="border-b border-gray-200 text-xs font-semibold text-gray-400 uppercase tracking-wider bg-gray-50/70">

                            <th className="py-3 px-4">
                                Vehicle ID
                            </th>

                            <th className="py-3 px-4">
                                Plate Number
                            </th>

                            <th className="py-3 px-4">
                                Vehicle Model
                            </th>

                            <th className="py-3 px-4">
                                Vehicle Department
                            </th>

                            <th className="py-3 px-4">
                                Status
                            </th>

                        </tr>

                    </thead>



                    <tbody className="divide-y divide-gray-100 text-sm text-gray-700">


                        {vehicles.length === 0 ? (

                            <tr>

                                <td 
                                    colSpan="5" 
                                    className="text-center py-8 text-gray-400 italic"
                                >

                                    No vehicles found. Check backend connection to 
                                    /api/vehicles/all

                                </td>

                            </tr>


                        ) : (


                            vehicles.map(vehicle => (

                                <tr 
                                    key={vehicle.id} 
                                    className="hover:bg-gray-50/80 transition-colors"
                                >


                                    <td className="py-3 px-4 text-gray-400 font-medium">
                                        #{vehicle.id}
                                    </td>



                                    <td className="py-3 px-4 font-mono font-bold text-gray-900">
                                        {vehicle.plateNumber || vehicle.plate_number}
                                    </td>



                                    <td className="py-3 px-4 text-gray-600 font-medium">
                                        {vehicle.model}
                                    </td>



                                    <td className="py-3 px-4 text-gray-500">
                                        {vehicle.department}
                                    </td>



                                    <td className="py-3 px-4">

                                        <span className="px-2 py-0.5 text-xs font-bold bg-green-50 text-green-700 rounded-full border border-green-200">

                                            {vehicle.status || 'Active'}

                                        </span>

                                    </td>


                                </tr>

                            ))

                        )}


                    </tbody>


                </table>


            </div>



            {/* Pagination Buttons */}

            <div className="flex justify-center items-center gap-5 mt-6">


                <button

                    disabled={page === 0}

                    onClick={() => setPage(page - 1)}

                    className="px-4 py-2 bg-blue-600 text-white rounded disabled:bg-gray-300"

                >

                    Previous

                </button>



                <span className="text-gray-600 font-medium">

                    Page {page + 1} of {totalPages}

                </span>




                <button

                    disabled={page === totalPages - 1}

                    onClick={() => setPage(page + 1)}

                    className="px-4 py-2 bg-blue-600 text-white rounded disabled:bg-gray-300"

                >

                    Next

                </button>


            </div>



        </div>

    );

}


export default VehicleManager;
