package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        // 1. Svi mogu da gledaju parking
                        .pathMatchers(HttpMethod.GET, "/lots/**", "/spots/**", "/zone-pricing/**").permitAll()

                        // 2. User sme da pravi i vidi svoje rezervacije
                        .pathMatchers(HttpMethod.POST, "/reservations/**").hasAnyRole("USER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/reservations/user/**").hasAnyRole("USER", "ADMIN")

                        // 3. Admin vidi sve rezervacije
                        .pathMatchers(HttpMethod.GET, "/reservations").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/reservations/**").hasRole("ADMIN")

                        // 4. Admin menja parking
                        .pathMatchers(HttpMethod.POST, "/lots/**", "/spots/**", "/zone-pricing/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/lots/**", "/spots/**", "/zone-pricing/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/lots/**", "/spots/**", "/zone-pricing/**").hasRole("ADMIN")

                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);

        return http.build();
    }


    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(user, admin);
    }
}
