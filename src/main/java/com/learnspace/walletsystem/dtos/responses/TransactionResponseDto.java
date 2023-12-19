package com.learnspace.walletsystem.dtos.responses;

import com.learnspace.walletsystem.models.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResponseDto {
    private String id;
    private String walletId;
    private BigDecimal amount;
    private Status status;
}
