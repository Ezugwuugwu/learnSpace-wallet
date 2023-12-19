package com.learnspace.walletsystem.security.config;


import com.learnspace.walletsystem.security.filters.JwtAuthorizationFilter;
import com.learnspace.walletsystem.security.filters.SyncUsernamePasswordAuthenticationFilter;
import com.learnspace.walletsystem.security.manager.SyncAuthenticationManager;
import com.learnspace.walletsystem.security.providers.SyncUsernamePasswordAuthenticationProvider;
import com.learnspace.walletsystem.security.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Set;

import static com.learnspace.walletsystem.models.Role.ADMIN;
import static com.learnspace.walletsystem.models.Role.USER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;



    @Bean
    public AuthenticationManager authenticationManager(){
        Set<AuthenticationProvider> providers = Set.of(new SyncUsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder));
        return new SyncAuthenticationManager(providers);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var authenticationFilter = new SyncUsernamePasswordAuthenticationFilter(authenticationManager(), jwtService);
        authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(c->c.configurationSource(getUrlBasedCorsConfigurationSource()))
                .sessionManagement(c->c.sessionCreationPolicy(STATELESS))
                .addFilterBefore(authenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtService), authenticationFilter.getClass())
                .authorizeHttpRequests(c->c.requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/user").permitAll())
                .authorizeHttpRequests(c->c.requestMatchers(HttpMethod.POST, "/api/v1/wallet").hasAnyAuthority(USER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/wallet").hasAnyAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/user/**").hasAnyAuthority(USER.name(), ADMIN.name()))
                .authorizeHttpRequests(c->c.anyRequest().authenticated())
                .build();
    }

    private static UrlBasedCorsConfigurationSource getUrlBasedCorsConfigurationSource() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("*"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configurationSource.registerCorsConfiguration("*", corsConfig);
        return configurationSource;
    }


}
