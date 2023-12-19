package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.TransactionRequestDto;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;
import com.learnspace.walletsystem.models.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TransactionServiceImplTest {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletService walletService;

    @Test
    void testCreateTransaction(){
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setAmount(BigDecimal.TEN);
        var response = transactionService.create(transactionRequestDto);
        assertThat(response.getId()).isNotNull();
    }

    @Test
    void getTransactionByReferenceTest() {
        assertThat(transactionService.getTransactionByReference("flotk1vssl")).isNotNull();
    }

    @Test
    public void verifyTransactionTest(){
        String reference = "flotk1vssl";
        WalletResponseDTO foundWallet = walletService.getWalletByTransactionReference(reference);
        BigDecimal oldBalance = foundWallet.getBalance();
        transactionService.verifyTransaction(reference);
        foundWallet = walletService.getWalletByTransactionReference(reference);
        BigDecimal newBalance = foundWallet.getBalance();
        assertThat(newBalance).isGreaterThanOrEqualTo(oldBalance);
        assertThat(transactionService.getTransactionByReference(reference).getStatus()).isEqualTo(Status.SUCCESSFUL);
    }
}