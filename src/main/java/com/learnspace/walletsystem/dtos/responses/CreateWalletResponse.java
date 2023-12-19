package com.learnspace.walletsystem.dtos.responses;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CreateWalletResponse {
    private String id;
    private String userId;
    private BigDecimal balance = BigDecimal.ZERO;
    private LocalDateTime createdDate;
}
