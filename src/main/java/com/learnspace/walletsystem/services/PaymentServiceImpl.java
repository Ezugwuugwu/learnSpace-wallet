package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.PaymentRequestDto;
import com.learnspace.walletsystem.dtos.responses.PaymentResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Slf4j
@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    @Override
    public PaymentResponseDto pay(String walletId, PaymentRequestDto paymentRequestDto) {
        String paystackEndpoint = "https://api.paystack.co/transaction/initialize";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String key = "sk key";
        headers.set("Authorization", "Bearer "+key);
        HttpEntity<PaymentRequestDto> httpEntity = new HttpEntity<>(paymentRequestDto, headers);
        var response = restTemplate.postForEntity(paystackEndpoint, httpEntity, PaymentResponseDto.class);

        return response.getBody();
    }

    @Override
    public PaymentResponseDto verify(String reference) {
        String verifyUrl = "https://api.paystack.co/transaction/verify/".concat(reference);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String key = "sk key";
        headers.set("Authorization", "Bearer "+key);
        HttpEntity<PaymentRequestDto> httpEntity = new HttpEntity<>(null, headers);
        var response = restTemplate.exchange(verifyUrl, HttpMethod.GET, httpEntity, PaymentResponseDto.class);
        return response.getBody();
    }
}
