package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.RegisterRequest;
import com.learnspace.walletsystem.dtos.responses.RegisterResponse;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;

public interface UserService {
    RegisterResponse register(RegisterRequest request);

    WalletResponseDTO getWalletFor(String userId);
}
