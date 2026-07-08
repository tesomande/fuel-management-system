package com.FuelMgt.Fuel.Management.System.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;
import com.FuelMgt.Fuel.Management.System.Entity.FuelTransaction;
import com.FuelMgt.Fuel.Management.System.Repository.FuelStockRepository;
import com.FuelMgt.Fuel.Management.System.Repository.FuelTransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class FuelTransactionService {
	
	 @Autowired
	    private FuelTransactionRepository transactionRepo;

	    @Autowired
	    private FuelStockRepository stockRepo;

	    @Transactional
	    public FuelTransaction save(FuelTransaction tx) {

	    	 // 1. Get stock
	        FuelStock stock = stockRepo.findByFuelType(tx.getFuelType());
	        if (stock == null) {
	            throw new RuntimeException("Fuel stock not found for type: " + tx.getFuelType());
	        }
	        // 2. Validate liters
	        if (tx.getLiters() == null || tx.getLiters() <= 0) {
	            throw new RuntimeException("Invalid fuel quantity");
	        }
	     // 3. Check stock availability
	        if (stock.getQuantityLiters() < tx.getLiters()) {
	            throw new RuntimeException("Insufficient fuel stock. Available: " + stock.getQuantityLiters());
	        }

	        
	        if (stock.getQuantityLiters() < tx.getLiters()) {
	            throw new RuntimeException("Insufficient fuel stock");
	        }
	        
	        // 4. Deduct stock automatically
	        stock.setQuantityLiters(
	            stock.getQuantityLiters() - tx.getLiters()
	        );
	        stockRepo.save(stock);
	        
	     // 5. Set transaction date
	        tx.setTransactionDate(LocalDateTime.now());
	        
	        // 6. Save transaction
	        return transactionRepo.save(tx);

	    }

	    public List<FuelTransaction> getAll() {
	        return transactionRepo.findAll();
	    }

}
