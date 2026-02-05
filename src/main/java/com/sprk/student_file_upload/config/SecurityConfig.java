package com.sprk.student_file_upload.config;

import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails student= User
                .withUsername("sahil")
                .password("{noop}1234")
                .roles("USER")
                .build();

        UserDetails teacher= User
                .withUsername("teacher")
                .password("{noop}1234")
                .roles("ADMIN")
                .build();
        UserDetails both= User
                .withUsername("admin")
                .password("{noop}1234")
                .roles("ADMIN","USER")
                .build();

        UserDetailsService userDetailsService=new InMemoryUserDetailsManager(student,teacher,both);
        return userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.anyRequest().authenticated()).httpBasic(
                        Customizer.withDefaults()
                );

        return http.build();

    }
}
