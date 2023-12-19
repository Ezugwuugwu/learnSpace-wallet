package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.PaymentRequestDto;
import com.learnspace.walletsystem.dtos.responses.PaymentResponseDto;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;
import com.learnspace.walletsystem.models.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentServiceImplTest {
    @Autowired
    PaymentService paymentService;
    @Autowired
    private  WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @Test
    public void initializePayment_test(){

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setAmount(BigDecimal.valueOf(200));
        paymentRequestDto.setEmail("joel@gmail.com");

        PaymentResponseDto responseDto = paymentService.pay("657ddd27e043377356b38209", paymentRequestDto);
        assertThat(responseDto.getData().getAuthorizationUrl()).isNotNull();
        assertThat(responseDto.getData().getReference()).isNotNull();
    }

    @Test
    public void verifyPaymentTest(){
        String reference = "flotk1vssl";
        WalletResponseDTO foundWallet = walletService.getWalletByTransactionReference(reference);
        BigDecimal oldBalance = foundWallet.getBalance();
        paymentService.verify(reference);
        foundWallet = walletService.getWalletByTransactionReference(reference);
        BigDecimal newBalance = foundWallet.getBalance();
        assertThat(newBalance).isGreaterThan(oldBalance);
        assertThat(transactionService.getTransactionByReference(reference).getStatus()).isEqualTo(Status.SUCCESSFUL);
    }


}