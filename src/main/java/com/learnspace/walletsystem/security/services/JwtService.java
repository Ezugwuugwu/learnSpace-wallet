package com.learnspace.walletsystem.security.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateTokenFor(String username);

    boolean validate(String token);

    UserDetails extractUserFrom(String token);
}
