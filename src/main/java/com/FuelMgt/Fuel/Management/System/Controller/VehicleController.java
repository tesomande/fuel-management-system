package com.FuelMgt.Fuel.Management.System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FuelMgt.Fuel.Management.System.Entity.Vehicle;
import com.FuelMgt.Fuel.Management.System.Service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {
	
	@Autowired
    private VehicleService service;

    @PostMapping("/add")
    public Vehicle addVehicle(
            @RequestBody Vehicle vehicle) {

        return service.save(vehicle);
    }

    @GetMapping("/all")
    public List<Vehicle> getAll() {
        return service.getAll();
    }

}
