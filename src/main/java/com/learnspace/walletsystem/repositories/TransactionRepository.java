package com.learnspace.walletsystem.repositories;

import com.learnspace.walletsystem.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    Optional<Transaction> findByTransactionReference(String reference);

    List<Transaction> findByWalletId(String walletId);
}
