package com.FuelMgt.Fuel.Management.System.dto;

//import com.FuelMgt.Fuel.Management.System.dto.DashboardSummary;

public class DashboardSummary {
	
	private long totalVehicles;
	private long totalEmployees;
    private long totalTransactions;
    private long totalFuelTypes;
    private double totalFuelInStock;
    private long lowStockCount;

    public DashboardSummary() {
    }

    public DashboardSummary(long totalVehicles,
            long totalEmployees,
            long totalTransactions,
            long totalFuelTypes,
            double totalFuelInStock,
            long lowStockCount) {

this.totalVehicles = totalVehicles;
this.totalEmployees = totalEmployees;
this.totalTransactions = totalTransactions;
this.totalFuelTypes = totalFuelTypes;
this.totalFuelInStock = totalFuelInStock;
this.lowStockCount = lowStockCount;
    }

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }
    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public long getTotalFuelTypes() {
        return totalFuelTypes;
    }

    public void setTotalFuelTypes(long totalFuelTypes) {
        this.totalFuelTypes = totalFuelTypes;
    }

    public double getTotalFuelInStock() {
        return totalFuelInStock;
    }

    public void setTotalFuelInStock(double totalFuelInStock) {
        this.totalFuelInStock = totalFuelInStock;
    }

    public long getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(long lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

}
