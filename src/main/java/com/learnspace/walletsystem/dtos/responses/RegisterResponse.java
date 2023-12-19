package com.learnspace.walletsystem.dtos.responses;


import lombok.Data;

@Data
public class RegisterResponse {
    private String walletId;
    private String balance;
    private String email;
}
