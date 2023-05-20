package com.thaieats.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends GenericFilterBean {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/resources/**", "/signup", "/api/a").permitAll()
                        .requestMatchers("/api/user").access(AuthorizationManagers.anyOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("USER")))
                        .requestMatchers("/api/super").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("SUPER")))
                        .requestMatchers("/api/admin").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("zxcv")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("zxcv")
                .roles("ADMIN")
                .build();

        UserDetails superUser = User.withDefaultPasswordEncoder()
                .username("super")
                .password("zxcv")
                .roles("ADMIN","SUPER")
                .build();

        return new InMemoryUserDetailsManager(user, admin, superUser);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // do something before the rest of the application
        System.out.println("The parameter str is : " + request.getParameter("str"));
        filterChain.doFilter(request, response);
        // do something after the rest of the application
    }
}
