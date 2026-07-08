package com.FuelMgt.Fuel.Management.System.dto;

public class StockRefillRequest {
	
	 private String fuelType;
	    private Double liters;

	    public String getFuelType() { return fuelType; }
	    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

	    public Double getLiters() { return liters; }
	    public void setLiters(Double liters) { this.liters = liters; }
    public StockRefillRequest() {
    	
    }

	public StockRefillRequest(String fuelType, Double liters) {
		super();
		this.fuelType = fuelType;
		this.liters = liters;
	}

	

}
