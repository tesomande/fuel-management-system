package com.FuelMgt.Fuel.Management.System.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 1. Force CORS processing BEFORE any security filters
            .addFilterBefore(corsFilter(), ChannelProcessingFilter.class)

            // 2. Disable CSRF for stateless API
            .csrf(csrf -> csrf.disable())

            // 3. Stateless sessions
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            	    // Authentication
            	    .requestMatchers("/api/auth/**").permitAll()
            	    
            	   // .requestMatchers(HttpMethod.POST, "/api/**").permitAll()

            	    // Swagger/OpenAPI
            	    .requestMatchers(
            	        "/swagger-ui/**",
            	        "/swagger-ui.html",
            	        "/v3/api-docs/**",
            	        "/v3/api-docs",
            	        "/swagger-resources/**",
            	        "/webjars/**"
            	    ).permitAll()

            	    // API
            	    .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
            	    .requestMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("ADMIN", "MANAGER")
            	    .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("ADMIN", "MANAGER")
            	    .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ADMIN")

            	    .anyRequest().authenticated()
            	)

            // 5. Add JWT Filter
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow all local & docker frontend origins
        config.setAllowedOriginPatterns(List.of(
                "http://localhost:3000",
                "http://localhost:3001",
                "http://localhost:5173",
                "http://127.0.0.1:3001"
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}