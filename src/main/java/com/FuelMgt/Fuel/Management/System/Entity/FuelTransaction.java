package com.FuelMgt.Fuel.Management.System.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class FuelTransaction {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String fuelType;
	    private Double liters;
	    private LocalDateTime transactionDate;
	    private String driverName;

	    @ManyToOne
	    @JoinColumn(name = "vehicle_id")
	    private Vehicle vehicle;
	    
	    public FuelTransaction() {
	    }

		public FuelTransaction(Long id, String fuelType, Double liters, LocalDateTime transactionDate,
				String driverName, Vehicle vehicle) {
			super();
			this.id = id;
			this.fuelType = fuelType;
			this.liters = liters;
			this.transactionDate = transactionDate;
			this.driverName = driverName;
			this.vehicle = vehicle;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFuelType() {
			return fuelType;
		}

		public void setFuelType(String fuelType) {
			this.fuelType = fuelType;
		}

		public Double getLiters() {
			return liters;
		}

		public void setLiters(Double liters) {
			this.liters = liters;
		}

		public LocalDateTime getTransactionDate() {
			return transactionDate;
		}

		public void setTransactionDate(LocalDateTime transactionDate) {
			this.transactionDate = transactionDate;
		}

		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}

		public Vehicle getVehicle() {
			return vehicle;
		}

		public void setVehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
		}
	    
	    
	}


