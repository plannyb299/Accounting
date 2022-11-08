package com.example.financeservice.transaction.service;


import com.example.financeservice.transaction.dto.CreateTransactionDto;
import com.example.financeservice.transaction.dto.ViewTransactionDto;


public interface TransactionStrategy {

  ViewTransactionDto createTransaction(CreateTransactionDto transactionDto);
}
