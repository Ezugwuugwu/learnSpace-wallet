package com.learnspace.walletsystem.config;


import com.learnspace.walletsystem.models.User;
import com.learnspace.walletsystem.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.learnspace.walletsystem.models.Role.ADMIN;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
        String adminEmail = "admin@learnspace.africa";
        return (args)-> {
            if (!userRepository.existsById(adminEmail)) {
                User user = new User();
                user.getRoles().add(ADMIN);
                user.setEmail(adminEmail);
                user.setPassword(passwordEncoder.encode("password"));
                userRepository.save(user);
            }
        };

    }
}
