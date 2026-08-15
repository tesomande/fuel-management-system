package com.FuelMgt.Fuel.Management.System.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FuelMgt.Fuel.Management.System.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
