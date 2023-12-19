package com.learnspace.walletsystem.dtos.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {
    private String senderId;
    private BigDecimal amount;
    private String reference;
    private String walletId;
    private String authorizationUrl;

}
