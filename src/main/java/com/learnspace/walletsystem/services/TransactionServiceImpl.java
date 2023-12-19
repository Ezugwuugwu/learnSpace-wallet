package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.responses.ApiResponse;
import com.learnspace.walletsystem.dtos.requests.TransactionRequestDto;
import com.learnspace.walletsystem.dtos.responses.TransactionResponseDto;
import com.learnspace.walletsystem.exception.NotFoundException;
import com.learnspace.walletsystem.models.Transaction;
import com.learnspace.walletsystem.models.Wallet;
import com.learnspace.walletsystem.repositories.TransactionRepository;
import com.learnspace.walletsystem.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.learnspace.walletsystem.models.Status.*;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;
    private final WalletRepository walletRepository;
    @Override
    public Transaction create(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = loadTransactionDetails(transactionRequestDto);
        return transactionRepository.save(transaction);
    }

    private Transaction loadTransactionDetails(TransactionRequestDto transactionRequestDto) {
        return Transaction.builder()
                .amount(transactionRequestDto.getAmount())
                .transactionReference(transactionRequestDto.getReference())
                .walletId(transactionRequestDto.getWalletId())
                .senderId(transactionRequestDto.getSenderId())
                .authorizationUrl(transactionRequestDto.getAuthorizationUrl())
                .status(PENDING)
                .build();
    }


    @Override
    public List<Transaction> getTransactionsFor(String walletId) {
        return transactionRepository.findByWalletId(walletId);
    }

    @Override
    public TransactionResponseDto getTransactionByReference(String reference) {
        Transaction foundTransaction = transactionRepository.findByTransactionReference(reference).orElseThrow(()->new NotFoundException(
                String.format("transaction with reference %s not found", reference)
        ));
        return modelMapper.map(foundTransaction, TransactionResponseDto.class);
    }

    @Override
    public ApiResponse<String> verifyTransaction(String reference) {
        var response = paymentService.verify(reference);
        var foundTransaction =transactionRepository.findByTransactionReference(reference)
                .orElseThrow(()->new NotFoundException("transaction not found"));
        if (response.getData().getStatus().equals("success")) {
            foundTransaction.setStatus(SUCCESSFUL);
            transactionRepository.save(foundTransaction);

            Wallet wallet = walletRepository.findByUserId(foundTransaction.getSenderId())
                    .orElseThrow(() -> new NotFoundException("Wallet not found for user"));
            BigDecimal transactionAmount = foundTransaction.getAmount();
            BigDecimal currentBalance = wallet.getBalance();
            BigDecimal newBalance = currentBalance.add(transactionAmount);
            wallet.setBalance(newBalance);
            walletRepository.save(wallet);

            return new ApiResponse<>(null, "Successful");
        }
        else {
            foundTransaction.setStatus(FAILED);
            transactionRepository.save(foundTransaction);
            return new ApiResponse<>(null, "Failed");
        }
    }

}
