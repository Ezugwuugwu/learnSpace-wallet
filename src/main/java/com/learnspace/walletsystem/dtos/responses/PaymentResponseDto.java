package com.learnspace.walletsystem.dtos.responses;

import com.learnspace.walletsystem.dtos.requests.PaymentData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDto {
    private boolean status;
    private String message;
    private PaymentData data;
}
