package com.FuelMgt.Fuel.Management.System.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class FuelStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Fuel type is required")
    @Size(min = 3, max = 20,
          message = "Fuel type must be between 3 and 20 characters")
    @Column(unique = true)
    private String fuelType;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Double quantityLiters;

    public FuelStock() {
    }

    public FuelStock(Long id, String fuelType, Double quantityLiters) {
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
}