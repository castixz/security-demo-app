package com.castixz.demo_security_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationManager<RequestAuthorizationContext> authorizationManager) throws Exception {
        return http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/admin").access(authorizationManager)
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        // create in memory details manager for simplification
        final var manager = new InMemoryUserDetailsManager();
        // create dummy users for testing purpose
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder().encode("dummypassword"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("user1")
                .password(bCryptPasswordEncoder().encode("dummypassword"))
                .roles("ADMIN")
                .build());
        manager.createUser(User.withUsername("user2")
                .password(bCryptPasswordEncoder().encode("dummypassword"))
                .roles("NO_ADMIN")
                .build());
        return manager;
    }

    @Bean
    AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager() {
        return (Supplier<Authentication> authentication, RequestAuthorizationContext context) -> {
            Authentication auth = authentication.get();
            final var isAdminRole = auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            final var isAdminUsername = auth.getName().equals("admin");
            return new AuthorizationDecision(isAdminRole || isAdminUsername);
        };
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
