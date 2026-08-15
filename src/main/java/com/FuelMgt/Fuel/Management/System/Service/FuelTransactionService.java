package com.FuelMgt.Fuel.Management.System.Service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;
import com.FuelMgt.Fuel.Management.System.Entity.FuelTransaction;
import com.FuelMgt.Fuel.Management.System.Repository.FuelStockRepository;
import com.FuelMgt.Fuel.Management.System.Repository.FuelTransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class FuelTransactionService {

    private final FuelTransactionRepository transactionRepo;
    private final FuelStockRepository stockRepo;

    public FuelTransactionService(FuelTransactionRepository transactionRepo, FuelStockRepository stockRepo) {
        this.transactionRepo = transactionRepo;
        this.stockRepo = stockRepo;
    }

    @Transactional
    public FuelTransaction save(FuelTransaction tx) {

        // 1. Validate input parameters
        if (tx.getFuelType() == null || tx.getFuelType().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fuel type is required.");
        }

        if (tx.getLiters() == null || tx.getLiters() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fuel liters must be greater than 0.");
        }

        // 2. Fetch current stock for the given fuel type
        FuelStock stock = stockRepo.findByFuelType(tx.getFuelType());
        if (stock == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Fuel stock record not found for type: " + tx.getFuelType()
            );
        }

        // 3. Verify stock availability
        if (stock.getQuantityLiters() < tx.getLiters()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                String.format("Insufficient fuel stock. Available: %.2f L, Requested: %.2f L", 
                    stock.getQuantityLiters(), tx.getLiters())
            );
        }

        // 4. DEDUCT FUEL STOCK & SAVE
        double remainingStock = stock.getQuantityLiters() - tx.getLiters();
        stock.setQuantityLiters(remainingStock);
        stockRepo.save(stock);

        // 5. Set timestamp if missing
        if (tx.getTransactionDate() == null) {
            tx.setTransactionDate(LocalDateTime.now());
        }

        // 6. Save and return transaction
        return transactionRepo.save(tx);
    }

    public Page<FuelTransaction> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepo.findAll(pageable);
    }
}