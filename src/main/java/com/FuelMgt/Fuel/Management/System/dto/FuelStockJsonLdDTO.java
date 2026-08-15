package com.FuelMgt.Fuel.Management.System.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.FuelMgt.Fuel.Management.System.Entity.FuelStock;

public class FuelStockJsonLdDTO {
	
	public static Map<String, Object> from(FuelStock stock) {

        Map<String, Object> jsonLd = new LinkedHashMap<>();

        //jsonLd.put("@context", "https://schema.org");
     // Create the @context object
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("@vocab", "http://localhost:7075/vocab#");

        jsonLd.put("@context", context);
        
        
        //jsonLd.put("@type", "Product");
        jsonLd.put("@type", "FuelStock");
        jsonLd.put("@id", "http://localhost:7075/api/stock/" + stock.getId());

        jsonLd.put("id", stock.getId());
        jsonLd.put("fuelType", stock.getFuelType());
        jsonLd.put("quantityLiters", stock.getQuantityLiters());

        return jsonLd;
    }

}
