package com.FuelMgt.Fuel.Management.System.Controller;

//import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FuelMgt.Fuel.Management.System.Entity.Vehicle;
import com.FuelMgt.Fuel.Management.System.Service.VehicleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicles", description = "Fleet Vehicle Operations")
//@CrossOrigin(origins = "http://localhost:3000")

@CrossOrigin(origins = {
	    "http://localhost:3000",
	    "http://localhost:3001"
	})
public class VehicleController {
	
	@Autowired
    private VehicleService service;

    @PostMapping("/add")
    public Vehicle addVehicle(
            @RequestBody Vehicle vehicle) {

        return service.save(vehicle);
    }

       //paggination
    @GetMapping("/all")
    @Operation(summary = "Get all vehicles", description = "Returns active fleet vehicle profiles")
    public Page<Vehicle> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getAll(page, size);
    }
    
    @PutMapping("/update/{id}")
    public Vehicle updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle vehicle) {

        return service.updateVehicle(id, vehicle);
    }
    
    @DeleteMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {

        service.deleteVehicle(id);

        return "Vehicle deleted successfully";
    }

}
