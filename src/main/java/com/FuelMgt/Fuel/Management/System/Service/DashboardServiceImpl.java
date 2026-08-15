package com.FuelMgt.Fuel.Management.System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;
import com.FuelMgt.Fuel.Management.System.Repository.FuelStockRepository;
import com.FuelMgt.Fuel.Management.System.Repository.FuelTransactionRepository;
import com.FuelMgt.Fuel.Management.System.dto.DashboardSummary;
import com.FuelMgt.Fuel.Management.System.Repository.VehicleRepository;
import com.FuelMgt.Fuel.Management.System.Repository.EmployeeRepository;


@Service
//public class DashboardServiceImpl<VehicleRepository> implements DashboardService {
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
    private VehicleRepository vehicleRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

    @Autowired
    private FuelStockRepository fuelStockRepository;

    @Autowired
    private FuelTransactionRepository fuelTransactionRepository;

    @Override
    public DashboardSummary getDashboardSummary() {

        long totalVehicles = vehicleRepository .count();
        //long totalEmployees = EmployeeRepository .count();
        long totalEmployees = employeeRepository.count();

        long totalTransactions = fuelTransactionRepository.count();

        long totalFuelTypes = fuelStockRepository.count();

        double totalFuelInStock =
                fuelStockRepository.findAll()
                        .stream()
                        .mapToDouble(FuelStock::getQuantityLiters)
                        .sum();

        long lowStock =
                fuelStockRepository.findAll()
                        .stream()
                        .filter(stock -> stock.getQuantityLiters() < 500)
                        .count();

        return new DashboardSummary(
                totalVehicles,
                totalEmployees,
                totalTransactions,
                totalFuelTypes,
                totalFuelInStock,
                lowStock
        );
    }

}
