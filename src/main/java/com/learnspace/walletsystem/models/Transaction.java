package com.learnspace.walletsystem.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
@ToString
public class Transaction {
    @Id
    private String id;
    private String senderId;
    private String walletId;
    private BigDecimal amount;
    private LocalDateTime transactionDate=now();
    private Status status;
    private String transactionReference;
    private String authorizationUrl;

}
