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
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import com.FuelMgt.Fuel.Management.System.dto.FuelStockJsonLdDTO;

@RestController
//@CrossOrigin(origins = "http://localhost:3000") // ◄ This permits React to securely pull the stream

@CrossOrigin(origins = {
	    "http://localhost:3000",
	    "http://localhost:3001"
	})
@RequestMapping("/api/stock")
public class FuelStockController {
	
	 @Autowired  //It tells the Spring container to automatically provide (inject) an object (bean) that your class depends on.
	    private FuelStockService service;
	 
	 @Autowired
	    private FuelStockService fuelStockService;
	       
	    @PostMapping("/add")
	    public FuelStock addStock(@Valid @RequestBody FuelStock stock,BindingResult result) {
	    	
	    	 System.out.println("Has Errors = " + result.hasErrors());

	    	 result.getAllErrors().forEach(System.out::println);
	        return service.addStock(stock.getFuelType(), stock.getQuantityLiters());
	    }
	    @PostMapping("/validation-test")
	    public String validationTest(
	            @Valid @RequestBody FuelStock stock,
	            org.springframework.validation.BindingResult result) {

	        System.out.println("Has Errors = " + result.hasErrors());

	        result.getAllErrors().forEach(System.out::println);

	        return "VALID";
	    }
	    	    
	    @GetMapping("/all")
	    public List<FuelStock> getAll() {
	        return service.getAll();
	    }
	    //
	    @GetMapping("/{id}/jsonld")
	    public Map<String, Object> getFuelStockJsonLd(@PathVariable Long id) {

	        FuelStock stock = service.getById(id);

	        return FuelStockJsonLdDTO.from(stock);
	    }

	    // Add this method here
	    @PostMapping("/refill")
	    public FuelStock refillStock(@Valid @RequestBody StockRefillRequest request) {

	        return service.addStock(
	            request.getFuelType(),
	            request.getLiters()
	        );
	    }
	    
	    @PutMapping("/update/{id}")
	    public FuelStock updateStock(@PathVariable Long id,
	                                 @Valid @RequestBody FuelStock stock) {

	        return service.updateStock(id, stock);
	    
	    }
	    
	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteStock(@PathVariable Long id) {

	        fuelStockService.deleteStock(id);

	        return ResponseEntity.ok("Fuel stock deleted successfully");
	    }
}
