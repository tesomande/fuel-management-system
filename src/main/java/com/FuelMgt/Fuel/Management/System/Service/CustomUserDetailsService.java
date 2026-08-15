package com.FuelMgt.Fuel.Management.System.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.FuelMgt.Fuel.Management.System.Entity.User;
import com.FuelMgt.Fuel.Management.System.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        System.out.println("========== LOADING USER FOR AUTHENTICATION ==========");
        System.out.println("Username: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Get the exact raw string name of the Enum (e.g., "USER", "ADMIN", "MANAGER")
        String roleName = user.getRole().name().toUpperCase();
        System.out.println("Assigned Role from DB: " + roleName);

        // Explicitly map it as an exact authority string match
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(roleName)) // Clean, exact string match tracking
                .build();
    }
}