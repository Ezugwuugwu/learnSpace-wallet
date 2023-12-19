package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.FundWalletRequest;
import com.learnspace.walletsystem.dtos.responses.CreateWalletResponse;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WalletServiceImplTest {

    @Autowired
    private WalletService walletService;

    @Test
    public void testCreateWallet(){

        CreateWalletResponse response = walletService.createWallet("ezugwuugw@gmail.com");

        assertThat(response).isNotNull();
    }


    @Test
    void testFundWallet() {
        FundWalletRequest request = new FundWalletRequest();
        request.setWalletId("657ef4a0c85ee47198c9b42e");
        request.setAmount("2000");
        request.setSenderId("john@email.com");
        WalletResponseDTO response = walletService.fundWallet(request);

        assertThat(response).isNotNull();
        assertThat(response.getTransactions().size()).isEqualTo(2);
    }

    @Test
    public void testGetWalletByTransactionReference(){
        WalletResponseDTO foundWallet = walletService.getWalletByTransactionReference("flotk1vssl");
        assertThat(foundWallet).isNotNull();
    }
}