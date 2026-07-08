package com.FuelMgt.Fuel.Management.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FuelMgt.Fuel.Management.System.Entity.Vehicle;

public interface VehicleRepositor extends JpaRepository<Vehicle, Long>{
	
	
}
