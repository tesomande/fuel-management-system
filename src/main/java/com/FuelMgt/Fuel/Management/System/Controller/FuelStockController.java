package com.FuelMgt.Fuel.Management.System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;
import com.FuelMgt.Fuel.Management.System.Service.FuelStockService;
import com.FuelMgt.Fuel.Management.System.dto.StockRefillRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // ◄ This permits React to securely pull the stream
@RequestMapping("/api/stock")
public class FuelStockController {
	
	 @Autowired
	    private FuelStockService service;

	   // @PostMapping("/add")
	   // public FuelStock addStock(@RequestBody FuelStock stock) {
	   //     return service.save(stock);
	   // }
	    
	    @PostMapping("/add")
	    public FuelStock addStock(@RequestBody FuelStock stock) {
	        return service.addStock(stock.getFuelType(), stock.getQuantityLiters());
	    }
	    
	    

	    @GetMapping("/all")
	    public List<FuelStock> getAll() {
	        return service.getAll();
	    }

	    // Add this method here
	    @PostMapping("/refill")
	    public FuelStock refillStock(@RequestBody StockRefillRequest request) {

	        return service.addStock(
	            request.getFuelType(),
	            request.getLiters()
	        );
	    }
	    

}
