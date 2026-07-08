package com.FuelMgt.Fuel.Management.System.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.Vehicle;
import com.FuelMgt.Fuel.Management.System.Repository.VehicleRepositor;


@Service
public class VehicleService {
	
	 @Autowired
	    private VehicleRepositor repo;

	    public Vehicle save(Vehicle vehicle) {
	        return repo.save(vehicle);
	    }

	    public List<Vehicle> getAll() {
	        return repo.findAll();
	    }

	    public Vehicle getById(Long id) {
	        return repo.findById(id).orElse(null);
	    }

	    public void delete(Long id) {
	        repo.deleteById(id);
	    }

}
