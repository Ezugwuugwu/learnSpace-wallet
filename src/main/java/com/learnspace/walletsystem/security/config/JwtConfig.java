package com.learnspace.walletsystem.security.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    private final String jwtSecretKey="secret";

    private final String jwtDuration="7";


    public String getJwtSecret(){
        return jwtSecretKey;
    }
    public int getJwtDuration(){
        return Integer.parseInt(jwtDuration);
    }
}
