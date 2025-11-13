package com.website.Shyne_jewelry.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
    public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter){
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            // Public endpoints (no authentication needed)
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
                                    "/swagger-resources/**", "/").permitAll()
                            .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register","/api/v1/cart/**","/api/v1/orders/**","/api/v1/payments/**").permitAll()


                            // Products - GET is public, POST/PUT/DELETE require ADMIN
                            .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")

                            // Admin endpoints
                            .requestMatchers("/api/v1/auth/register/admin").hasRole("ADMIN")
                            .requestMatchers("/api/v1/auth/login/admin").hasRole("ADMIN")
                            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                            // Everything else requires authentication
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        // Encrypt Password from plain text in database.
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

