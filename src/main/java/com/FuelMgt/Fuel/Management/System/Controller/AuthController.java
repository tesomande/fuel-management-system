package com.FuelMgt.Fuel.Management.System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.FuelMgt.Fuel.Management.System.config.JwtUtil;
import com.FuelMgt.Fuel.Management.System.dto.AuthResponse;
import com.FuelMgt.Fuel.Management.System.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtUtil jwt;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            System.out.println("👉 [STEP 1] Login request received for user: " + request.getUsername());

            // This performs the database password comparison
            Authentication auth = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            System.out.println("👉 [STEP 2] Authentication successful!");

            // This generates the token string
            String token = jwt.generateToken(request.getUsername());
            System.out.println("👉 [STEP 3] JWT Token generated successfully!");

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (Exception e) {
            System.out.println("❌ ERROR IN LOGIN CONTROLLER METHOD: " + e.getMessage());
            e.printStackTrace(); // Prints the entire stack trace to your Eclipse/IntelliJ console
            
            // Sends the exact message back to Postman body so we can read it
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + e.getMessage());
        }
    }
}