package com.learnspace.walletsystem.services;

import com.learnspace.walletsystem.dtos.requests.FundWalletRequest;
import com.learnspace.walletsystem.dtos.requests.PaymentRequestDto;
import com.learnspace.walletsystem.dtos.requests.TransactionRequestDto;
import com.learnspace.walletsystem.dtos.responses.CreateWalletResponse;
import com.learnspace.walletsystem.dtos.responses.PaymentResponseDto;
import com.learnspace.walletsystem.dtos.responses.TransactionResponseDto;
import com.learnspace.walletsystem.dtos.responses.WalletResponseDTO;
import com.learnspace.walletsystem.exception.NotFoundException;
import com.learnspace.walletsystem.models.Transaction;
import com.learnspace.walletsystem.models.Wallet;
import com.learnspace.walletsystem.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final TransactionService transactionService;
    private final PaymentService paymentService;


    @Override
    public CreateWalletResponse createWallet(String userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCreatedDate(now());
        Wallet savedWallet = walletRepository.save(wallet);
        return modelMapper.map(savedWallet, CreateWalletResponse.class);
    }

    @Override
    public WalletResponseDTO fundWallet(FundWalletRequest fundWalletRequest) {
        var foundWallet = getWalletById(fundWalletRequest.getWalletId());
        PaymentRequestDto paymentRequest = buildPaymentRequest(fundWalletRequest);
        PaymentResponseDto paymentResponse = paymentService.pay(foundWallet.getId(), paymentRequest);
        TransactionRequestDto transactionRequest = buildTransactionRequest(fundWalletRequest, paymentResponse);
        Transaction transaction = transactionService.create(transactionRequest);
        foundWallet.getTransactions().add(transaction);
        var savedWallet = walletRepository.save(foundWallet);
        return buildWalletResponse(savedWallet);
    }

    @Override
    public WalletResponseDTO getWalletByTransactionReference(String reference) {
        TransactionResponseDto foundTransaction = transactionService.getTransactionByReference(reference);
        return modelMapper.map(getWalletById(foundTransaction.getWalletId()), WalletResponseDTO.class);
    }

    @Override
    public WalletResponseDTO getWallet(String walletId) {
        return modelMapper.map(getWalletById(walletId), WalletResponseDTO.class);
    }

    @Override
    public List<WalletResponseDTO> getWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        return wallets.stream()
                .map(wallet -> modelMapper.map(wallet, WalletResponseDTO.class))
                .toList();
    }

    private WalletResponseDTO buildWalletResponse(Wallet savedWallet) {
        WalletResponseDTO walletResponse = new WalletResponseDTO();
        walletResponse.setCreatedDate(savedWallet.getCreatedDate());
        walletResponse.setBalance(savedWallet.getBalance());
        walletResponse.setId(savedWallet.getId());
        walletResponse.setTransactions(savedWallet.getTransactions());
        return walletResponse;
    }

    private TransactionRequestDto buildTransactionRequest(FundWalletRequest fundWalletRequest, PaymentResponseDto paymentResponse) {
        TransactionRequestDto transactionRequest = new TransactionRequestDto();
        transactionRequest.setReference(paymentResponse.getData().getReference());
        transactionRequest.setAmount(new BigDecimal(fundWalletRequest.getAmount()));
        transactionRequest.setWalletId(fundWalletRequest.getWalletId());
        transactionRequest.setSenderId(fundWalletRequest.getSenderId());
        transactionRequest.setAuthorizationUrl(paymentResponse.getData().getAuthorizationUrl());
        return transactionRequest;
    }

    private PaymentRequestDto buildPaymentRequest(FundWalletRequest fundWalletRequest) {
        PaymentRequestDto paymentRequest = new PaymentRequestDto();
        paymentRequest.setAmount(new BigDecimal(fundWalletRequest.getAmount()));
        paymentRequest.setEmail(fundWalletRequest.getSenderId());
        return paymentRequest;
    }

    private Wallet getWalletById(String walletId){
        return walletRepository.findById(walletId)
                .orElseThrow(()->new NotFoundException(String.format("Wallet with id %s not found", walletId)));
    }
}
