package com.learnspace.walletsystem.controllers;


import com.learnspace.walletsystem.dtos.requests.RegisterRequest;
import com.learnspace.walletsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserWallet(@PathVariable String userId){
        return ResponseEntity.ok(userService.getWalletFor(userId));
    }
}
