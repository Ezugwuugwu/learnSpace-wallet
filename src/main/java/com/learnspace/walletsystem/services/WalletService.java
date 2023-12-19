package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.responses.CreateWalletResponse;
import com.learnspace.walletsystem.dtos.requests.FundWalletRequest;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;

import java.util.List;

public interface WalletService {

    CreateWalletResponse createWallet(String userId);

    WalletResponseDTO fundWallet(FundWalletRequest walletRequest);

    WalletResponseDTO getWalletByTransactionReference(String reference);

    WalletResponseDTO getWallet(String walletId);

    List<WalletResponseDTO> getWallets();

}
