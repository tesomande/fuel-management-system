package com.FuelMgt.Fuel.Management.System.Service;

//import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.Vehicle;
import com.FuelMgt.Fuel.Management.System.Repository.VehicleRepository;
//import com.FuelMgt.Fuel.Management.System.Repository.VehicleRepositor;


@Service
public class VehicleService {
	
	@Autowired
    private VehicleRepository repo;

    public Vehicle save(Vehicle vehicle) {
        return repo.save(vehicle);
    }

    public Page<Vehicle> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }

    public Vehicle getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
    
    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle vehicle = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setPlateNumber(updatedVehicle.getPlateNumber());
        vehicle.setModel(updatedVehicle.getModel());
        vehicle.setDepartment(updatedVehicle.getDepartment());
        vehicle.setStatus(updatedVehicle.getStatus());

        return repo.save(vehicle);
    }
    
    // DELETE Vehicle
    public void deleteVehicle(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id: " + id);
        }

        repo.deleteById(id);
    }

}
