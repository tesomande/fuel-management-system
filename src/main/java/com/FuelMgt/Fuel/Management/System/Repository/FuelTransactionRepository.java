package com.FuelMgt.Fuel.Management.System.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FuelMgt.Fuel.Management.System.Entity.FuelTransaction;

public interface FuelTransactionRepository extends JpaRepository<FuelTransaction, Long> {

}
