package com.learnspace.walletsystem.dtos.responses;

import com.learnspace.walletsystem.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponseDTO {
    private String id;
    private BigDecimal balance;
    private List<Transaction> transactions;
    private LocalDateTime createdDate;
}
