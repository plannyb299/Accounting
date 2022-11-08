package com.example.financeservice.transaction.service;


import com.example.financeservice.common.TransactionType;
import com.example.financeservice.transaction.dto.ViewTransactionDto;

import java.util.List;

public interface TransactionService {

  List<ViewTransactionDto> getTransactionHistoryByType(TransactionType transactionType, String email);

  List<ViewTransactionDto> getAllTransactions(String email);


}
