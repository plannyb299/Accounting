package com.example.financeservice.transaction.processors;


import com.example.financeservice.account.repository.AccountRepository;
import com.example.financeservice.exeption.BadRequestDataException;
import com.example.financeservice.model.Account;
import com.example.financeservice.model.Transaction;
import com.example.financeservice.transaction.dto.CreateTransactionDto;
import com.example.financeservice.transaction.dto.ViewTransactionDto;
import com.example.financeservice.transaction.repository.TransactionRepository;
import com.example.financeservice.transaction.service.TransactionStrategy;
import com.example.financeservice.utils.CheckDetailsUtil;

import java.util.Objects;

public class DepositProcessor implements TransactionStrategy {

  private static TransactionRepository transactionRepository;
  private static AccountRepository accountRepository;


  public DepositProcessor() {

  }

  public DepositProcessor(TransactionRepository transactionRepository, AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public ViewTransactionDto createTransaction(CreateTransactionDto transactionDto) {
    Account account = CheckDetailsUtil.checkAccountInfo(transactionDto.getAccountNumber());
    Transaction transaction = null;
    if (!Objects.isNull(account)) {
      transaction = CheckDetailsUtil.getTransaction(transactionDto, account);
      transaction = CheckDetailsUtil.saveTransaction(transaction);

    } else {
      throw new BadRequestDataException("The Account provided is not valid:" + transactionDto.getAccountNumber() + " we can not complete the transaction");
    }
    //update/credit deposit account
    account.setAccountBalance(transactionDto.getAmount() + account.getAccountBalance());
    CheckDetailsUtil.updateAccount(account);
    return CheckDetailsUtil.getViewTransactionDto(transaction, account);

  }

}