package com.FuelMgt.Fuel.Management.System.Service;

import java.util.List;

//import org.apache.catalina.startup.ClassLoaderFactory.Repository;
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
    	
    	// Business validation
        if (fuelType == null || fuelType.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Fuel type is required");
        }

        if (liters == null || liters <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }


        fuelType = fuelType.trim().toLowerCase();

        System.out.println("fuelType = " + fuelType);
        System.out.println("liters = " + liters);


        FuelStock stock = repo.findByFuelType(fuelType);


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
    //semantic
    public FuelStock getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel stock not found"));
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
    
    public FuelStock updateStock(Long id, FuelStock updatedStock) {

        FuelStock stock = repo.findById(id)
                .orElseThrow(() -> 
                    new RuntimeException("Fuel stock not found"));

        stock.setFuelType(updatedStock.getFuelType());
        stock.setQuantityLiters(updatedStock.getQuantityLiters());

        return repo.save(stock);
    }
    
    public void deleteStock(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Fuel stock not found with id: " + id);
        }

        repo.deleteById(id);
    }
}
