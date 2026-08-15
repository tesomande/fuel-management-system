package com.FuelMgt.Fuel.Management.System.Controller;


import com.FuelMgt.Fuel.Management.System.dto.DashboardSummary;
import com.FuelMgt.Fuel.Management.System.Service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {
	
	 @Autowired
	    private DashboardService dashboardService;

	    @GetMapping("/summary")
	    public DashboardSummary getSummary() {

	        return dashboardService.getDashboardSummary();

	    }

}
