package com.thaieats.configuration;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends GenericFilterBean {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        //Sequence of Matchers is important; it requires checking privilege before role.
                        .requestMatchers(GET, "/api/v1/management/**").hasAuthority("READ")
                        .requestMatchers(POST, "/api/v1/management/**").hasAuthority("WRITE")
                        .requestMatchers(DELETE, "/api/v1/management/**").hasAuthority("DELETE")
                        .requestMatchers(new AntPathRequestMatcher("/h2/**"), new AntPathRequestMatcher("/api/v1/user/**")).permitAll() //Spring security 6.0.3 --> requestMatchers not working with /h2 path
                        .requestMatchers("/api/v1/management/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
/*                      .requestMatchers("/api/user").access(AuthorizationManagers.anyOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("USER")))
                        .requestMatchers("/api/super").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("SUPER_ADMIN")))
                        .requestMatchers("/api/admin").hasRole("ADMIN")*/
                        .anyRequest().denyAll())
                .headers().frameOptions().disable().and()
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


/*    @Bean
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
    }*/

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // do something before Pre-processing
        filterChain.doFilter(request, response);
        // do something after Post-processing
    }
}
