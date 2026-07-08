package com.FuelMgt.Fuel.Management.System.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;
import com.FuelMgt.Fuel.Management.System.Repository.FuelStockRepository;

@Service
public class FuelStockService {
	
	@Autowired
    private FuelStockRepository repo;

    // This is your existing method (No changes here)
    public FuelStock addStock(String fuelType, Double liters) {
    	
    	//fuelType = fuelType.trim();
    	fuelType = fuelType.trim().toLowerCase();
    	System.out.println("fuelType = " + fuelType);
        System.out.println("liters = " + liters);

        FuelStock stock = repo.findByFuelType(fuelType);
        System.out.println("Found stock = " + stock);

        if (stock == null) {
            stock = new FuelStock();
            stock.setFuelType(fuelType);
            stock.setQuantityLiters(liters);
        } else {
            stock.setQuantityLiters(
                stock.getQuantityLiters() + liters
            );
        }

        return repo.save(stock);
    }

    // 1. ADD THIS METHOD to fix service.save(stock) error
    public FuelStock save(FuelStock stock) {
        return repo.save(stock);
    }

    // 2. ADD THIS METHOD to fix service.getAll() error
    // Note: Make sure to import java.util.List at the top of this file!
    public List<FuelStock> getAll() {
        return repo.findAll();
    }
}
