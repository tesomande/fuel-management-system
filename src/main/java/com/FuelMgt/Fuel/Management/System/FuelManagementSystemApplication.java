package com.FuelMgt.Fuel.Management.System;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;


//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FuelManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuelManagementSystemApplication.class, args);
	}
		
	@Bean
    public CommandLineRunner seedDatabaseUsers(DataSource dataSource, PasswordEncoder passwordEncoder) {
        return args -> {
            String hash = passwordEncoder.encode("123");

            String query = "INSERT INTO public.users (username, password, role) VALUES (?, ?, ?) "
                         + "ON CONFLICT (username) DO UPDATE SET password = EXCLUDED.password, role = EXCLUDED.role";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                // 1. Regular User
                stmt.setString(1, "user");
                stmt.setString(2, hash);
                stmt.setString(3, "USER");
                stmt.addBatch();

                // 2. Manager
                stmt.setString(1, "manager");
                stmt.setString(2, hash);
                stmt.setString(3, "MANAGER");
                stmt.addBatch();

                // 3. Admin
                stmt.setString(1, "admin");
                stmt.setString(2, hash);
                stmt.setString(3, "ADMIN");
                stmt.addBatch();

                stmt.executeBatch();
                System.out.println("✅ DATABASE REPAIR: 'user', 'manager', and 'admin' seeded with password '123'!");

            } catch (Exception e) {
                System.out.println("❌ REPAIR ERROR: Could not talk to PostgreSQL -> " + e.getMessage());
            }
        };
    }
	

}
