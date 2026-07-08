package com.FuelMgt.Fuel.Management.System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FuelMgt.Fuel.Management.System.Entity.FuelTransaction;
import com.FuelMgt.Fuel.Management.System.Service.FuelTransactionService;

@RestController
@RequestMapping("/api/fuel")
@CrossOrigin(origins = "http://localhost:3000")
public class FuelTransactionController {
	
	 @Autowired
	    private FuelTransactionService service;

	    @PostMapping("/add")
	    public FuelTransaction add(@RequestBody FuelTransaction tx) {
	        return service.save(tx);
	    }

	    @GetMapping("/all")
	    public List<FuelTransaction> getAll() {
	        return service.getAll();
	    }

}
