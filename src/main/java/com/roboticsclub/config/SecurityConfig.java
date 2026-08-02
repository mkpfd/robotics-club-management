package com.roboticsclub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public resources
                        .requestMatchers("/css/**", "/js/**", "/403").permitAll()

                        // User Management (Admin only)
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        // Attendance
                        .requestMatchers("/attendance/**")
                        .hasAnyRole("ADMIN", "MENTOR")

                        // Member Management
                        .requestMatchers("/members/**")
                        .hasAnyRole("ADMIN", "MENTOR")

                        // Projects
                        .requestMatchers("/projects/**")
                        .authenticated()
                        .requestMatchers("/projects/edit/**", "/projects/update")
                        .hasAnyRole("ADMIN","MENTOR")

                        // Events
                        .requestMatchers("/events/**")
                        .authenticated()
                        .requestMatchers("/events/edit/**", "/events/update")
                        .hasAnyRole("ADMIN","MENTOR")

                        // Equipment
                        .requestMatchers("/equipment/**")
                        .hasAnyRole("ADMIN", "MENTOR")

                        // Dashboard
                        .requestMatchers("/dashboard")
                        .authenticated()

                        // Everything else requires login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/403")
                );

        return http.build();
    }
}
