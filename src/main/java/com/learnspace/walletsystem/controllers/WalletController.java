package com.learnspace.walletsystem.controllers;


import com.learnspace.walletsystem.dtos.requests.FundWalletRequest;
import com.learnspace.walletsystem.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<?> fundWallet(@RequestBody FundWalletRequest fundWalletRequest){
        return ResponseEntity.ok(walletService.fundWallet(fundWalletRequest));
    }

    @GetMapping
    public ResponseEntity<?> getAllWallets(){
        return ResponseEntity.ok(walletService.getWallets());
    }
}
