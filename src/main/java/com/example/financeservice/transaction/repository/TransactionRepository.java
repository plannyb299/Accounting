package com.example.financeservice.transaction.repository;


import com.example.financeservice.common.TransactionType;
import com.example.financeservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByEmail(String email);

  List<Transaction> findByAccountNumber(Long customerId);

  List<Transaction> findByTransactionTypeAndEmail(TransactionType transactionType, String email);

}
