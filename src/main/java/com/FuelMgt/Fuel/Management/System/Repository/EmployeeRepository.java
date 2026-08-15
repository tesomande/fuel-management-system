package com.FuelMgt.Fuel.Management.System.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FuelMgt.Fuel.Management.System.Entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByEmployeeId(String employeeId);

}
