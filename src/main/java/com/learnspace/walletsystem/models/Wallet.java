package com.learnspace.walletsystem.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Wallet {
    @Id
    private String id;
    private String userId;
    private BigDecimal balance = BigDecimal.ZERO;
    @DBRef
    private List<Transaction> transactions=new ArrayList<>();
    private LocalDateTime createdDate;


    public BigDecimal getBalance(){
        return transactions.stream()
                .filter(transaction -> transaction.getStatus().equals(Status.SUCCESSFUL))
                .map(transaction -> transaction.getAmount())
                .reduce(BigDecimal.ZERO, (a,b)->a.add(b));
    }
}
