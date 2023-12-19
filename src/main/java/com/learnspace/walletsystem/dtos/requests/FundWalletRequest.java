package com.learnspace.walletsystem.dtos.requests;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FundWalletRequest {
    private String senderId;
    private String amount;
    private String walletId;
}
