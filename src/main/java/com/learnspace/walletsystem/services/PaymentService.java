package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.PaymentRequestDto;
import com.learnspace.walletsystem.dtos.responses.PaymentResponseDto;


public interface PaymentService {


    PaymentResponseDto pay(String walletId, PaymentRequestDto paymentRequestDto);

    PaymentResponseDto verify(String reference);
}
