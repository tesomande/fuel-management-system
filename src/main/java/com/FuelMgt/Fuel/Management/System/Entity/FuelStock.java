package com.FuelMgt.Fuel.Management.System.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FuelStock {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(unique = true)
	private String fuelType;
    private Double quantityLiters;
    
    //Constructor
    public FuelStock() {
    	
    }

	public FuelStock(Long id, String fuelType, Double quantityLiters) {
		super();
		this.id = id;
		this.fuelType = fuelType;
		this.quantityLiters = quantityLiters;
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

	public Double getQuantityLiters() {
		return quantityLiters;
	}

	public void setQuantityLiters(Double quantityLiters) {
		this.quantityLiters = quantityLiters;
	}
    
    //getter and setter
    

}
