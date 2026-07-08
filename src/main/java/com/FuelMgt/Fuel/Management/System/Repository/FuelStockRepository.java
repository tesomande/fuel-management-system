package com.FuelMgt.Fuel.Management.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;

public interface FuelStockRepository extends JpaRepository<FuelStock, Long> {
	
	FuelStock findByFuelType(String fuelType);
	

}
