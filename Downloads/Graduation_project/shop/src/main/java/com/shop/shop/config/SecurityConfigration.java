package com.shop.shop.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/User/**").hasAnyRole("USER","ADMIN")
                    .requestMatchers(HttpMethod.POST, "/User/**").hasAnyRole("USER","ADMIN")
                    .requestMatchers(HttpMethod.GET, "/Car/**").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/Car/**").hasRole("USER")
                    .requestMatchers(HttpMethod.POST, "/Admin/**").hasRole("ADMIN")                    
                    );
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**") // Update this to your public endpoints
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider) // Ensure authenticationProvider is defined
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Ensure jwtAuthFilter is defined

        return http.build();
    }
}
