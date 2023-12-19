package com.learnspace.walletsystem.services;


import com.learnspace.walletsystem.dtos.requests.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testRegister(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("john@email.com");
        request.setPassword("password");
        var response = userService.register(request);
        assertNotNull(response);
    }
}
