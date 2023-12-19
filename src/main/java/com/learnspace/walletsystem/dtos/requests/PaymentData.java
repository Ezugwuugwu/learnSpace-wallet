package com.learnspace.walletsystem.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentData {
    @JsonProperty("authorization_url")
    private String authorizationUrl;
    private String reference;
    private String status;
    private BigDecimal amount;

}
