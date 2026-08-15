package com.FuelMgt.Fuel.Management.System.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Vehicle {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "plateNumber is required")
	    @Column(unique = true)
	    private String plateNumber;
	    
	    
	    private String model;
	    private String department;
	    private String status;
	    
	    @ManyToOne  // Table Relationship
	    @JoinColumn(name = "employee_id")
	    private Employee employee;

	    //constructor without and with parameter
	    public Vehicle() {
	    	
	    }

		public Vehicle(Long id, String plateNumber, String model, String department, String status) {
			super();
			this.id = id;
			this.plateNumber = plateNumber;
			this.model = model;
			this.department = department;
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPlateNumber() {
			return plateNumber;
		}

		public void setPlateNumber(String plateNumber) {
			this.plateNumber = plateNumber;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	    
	    // getters and setters
		
		
		

}
