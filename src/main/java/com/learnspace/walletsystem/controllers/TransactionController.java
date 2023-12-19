package com.learnspace.walletsystem.controllers;


import com.learnspace.walletsystem.dtos.responses.ApiResponse;
import com.learnspace.walletsystem.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<String>> verifyTransaction(@RequestParam String reference){
        return ResponseEntity.ok(transactionService.verifyTransaction(reference));
    }
}
