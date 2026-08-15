package com.FuelMgt.Fuel.Management.System.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.Employee;
import com.FuelMgt.Fuel.Management.System.Repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	 @Autowired
	    private EmployeeRepository employeeRepository;

	    // Add Employee
	    public Employee addEmployee(Employee employee) {

	        // Check if Employee ID already exists
	        if (employeeRepository.findByEmployeeId(employee.getEmployeeId()).isPresent()) {
	            throw new RuntimeException("Employee ID already exists.");
	        }

	        return employeeRepository.save(employee);
	    }

	    // Get All Employees
	    public List<Employee> getAllEmployees() {
	        return employeeRepository.findAll();
	    }

	    // Get Employee By ID
	    public Employee getEmployeeById(Long id) {

	        return employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
	    }

	    // Update Employee
	    public Employee updateEmployee(Long id, Employee updatedEmployee) {

	        Employee employee = employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

	        employee.setEmployeeId(updatedEmployee.getEmployeeId());
	        employee.setFirstName(updatedEmployee.getFirstName());
	        employee.setLastName(updatedEmployee.getLastName());
	        employee.setDepartment(updatedEmployee.getDepartment());
	        employee.setPosition(updatedEmployee.getPosition());
	        employee.setPhone(updatedEmployee.getPhone());
	        employee.setEmail(updatedEmployee.getEmail());
	        employee.setActive(updatedEmployee.isActive());

	        return employeeRepository.save(employee);
	    }

	    // Delete Employee
	    public void deleteEmployee(Long id) {

	        Employee employee = employeeRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

	        employeeRepository.delete(employee);
	    }

}
