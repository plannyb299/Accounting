package com.example.financeservice.transaction.processors;


import com.example.financeservice.account.repository.AccountRepository;
import com.example.financeservice.model.Account;
import com.example.financeservice.model.Transaction;
import com.example.financeservice.transaction.dto.CreateTransactionDto;
import com.example.financeservice.transaction.dto.ViewTransactionDto;
import com.example.financeservice.transaction.repository.TransactionRepository;
import com.example.financeservice.transaction.service.TransactionStrategy;
import com.example.financeservice.utils.CheckDetailsUtil;

public class WithdrawalProcessor implements TransactionStrategy {

  private AccountRepository accountRepository;
  private TransactionRepository transactionRepository;

  public WithdrawalProcessor() {

  }

  public WithdrawalProcessor(AccountRepository accountRepository, TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;

  }

  @Override
  public ViewTransactionDto createTransaction(CreateTransactionDto transactionDto) {
    //check if account exists else throw exception
    Account sourceAccount = CheckDetailsUtil.checkAccountInfo(transactionDto.getAccountNumber());
    // create transaction
    Transaction transaction = CheckDetailsUtil.getTransaction(transactionDto, sourceAccount);

    //check for bank account withdrawal limits

    double sourceAccountBalance = sourceAccount.getAccountBalance();
    double debitedAccountBalance = sourceAccountBalance - transactionDto.getAmount();

    //we make it here we are ready to commit debit in the source account
    sourceAccount.setAccountBalance(debitedAccountBalance);
    CheckDetailsUtil.updateAccount(sourceAccount);
    //save transaction
    CheckDetailsUtil.saveTransaction(transaction);

    return CheckDetailsUtil.getViewTransactionDto(transaction, sourceAccount);
  }


}
