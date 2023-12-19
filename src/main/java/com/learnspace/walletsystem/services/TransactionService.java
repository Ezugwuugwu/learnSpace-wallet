package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.responses.ApiResponse;
import com.learnspace.walletsystem.dtos.requests.TransactionRequestDto;
import com.learnspace.walletsystem.dtos.responses.TransactionResponseDto;
import com.learnspace.walletsystem.models.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction create(TransactionRequestDto transactionRequestDto);

    List<Transaction> getTransactionsFor(String walletId);

    TransactionResponseDto getTransactionByReference(String reference);

    ApiResponse<String> verifyTransaction(String reference);
}
