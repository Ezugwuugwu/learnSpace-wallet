package com.learnspace.walletsystem.security.filters;

import com.learnspace.walletsystem.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("POST")&&getPublicEndpoints().contains(request.getServletPath())){
            filterChain.doFilter(request, response);
        }else {
            String authHeader =  request.getHeader(AUTHORIZATION);
            if (authHeader!=null){
                String token = authHeader.substring("Bearer ".length());
                boolean isValidToken = jwtService.validate(token);
                if (isValidToken) {
                    UserDetails userDetails = jwtService.extractUserFrom(token);
                    Authentication authentication =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    public Set<String> getPublicEndpoints() {
        return Set.of("/api/v1/auth/login", "/api/v1/user");
    }
}
