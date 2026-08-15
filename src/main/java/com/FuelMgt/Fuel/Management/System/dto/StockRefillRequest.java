package com.FuelMgt.Fuel.Management.System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class StockRefillRequest {

    @NotBlank(message = "Fuel type is required")   //Bean Validation
    private String fuelType;

    @NotNull(message = "Liters is required")
    @Positive(message = "Liters must be greater than zero")
    private Double liters;

    public StockRefillRequest() {
    }

    public StockRefillRequest(String fuelType, Double liters) {
        this.fuelType = fuelType;
        this.liters = liters;
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
}