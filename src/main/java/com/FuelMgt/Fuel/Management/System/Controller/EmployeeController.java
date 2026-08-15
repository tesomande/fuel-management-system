package com.FuelMgt.Fuel.Management.System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.FuelMgt.Fuel.Management.System.Entity.Employee;
import com.FuelMgt.Fuel.Management.System.Service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Validated
@CrossOrigin(origins = "http://localhost:3000")


public class EmployeeController {
	
	 @Autowired
	    private EmployeeService employeeService;

	    // Add Employee
	    @PostMapping("/add")
	    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
	        return ResponseEntity.ok(employeeService.addEmployee(employee));
	    }

	    // Get All Employees
	    @GetMapping("/all")
	    public ResponseEntity<List<Employee>> getAllEmployees() {
	        return ResponseEntity.ok(employeeService.getAllEmployees());
	    }

	    // Get Employee By ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
	        return ResponseEntity.ok(employeeService.getEmployeeById(id));
	    }

	    // Update Employee
	    @PutMapping("/update/{id}")
	    public ResponseEntity<Employee> updateEmployee(
	            @PathVariable Long id,
	            @Valid @RequestBody Employee employee) {

	        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
	    }

	    // Delete Employee
	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

	        employeeService.deleteEmployee(id);

	        return ResponseEntity.ok("Employee deleted successfully.");
	    }

}
