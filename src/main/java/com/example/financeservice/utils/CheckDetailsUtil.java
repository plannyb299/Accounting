package com.example.financeservice.utils;


import com.example.financeservice.account.repository.AccountRepository;
import com.example.financeservice.exeption.BadRequestDataException;
import com.example.financeservice.model.Account;
import com.example.financeservice.model.Patient;
import com.example.financeservice.model.Transaction;
import com.example.financeservice.transaction.dto.CreateTransactionDto;
import com.example.financeservice.transaction.dto.ViewTransactionDto;
import com.example.financeservice.transaction.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class CheckDetailsUtil {

  //To try to agree to Dry principles
  private static AccountRepository accountRepository;
  //  private static UserRepository appUserRepository;
  private static TransactionRepository transactionRepository;

  private final Authentication authentication;

  @Autowired
  private static RestTemplate restTemplate;

  public static Account checkAccountInfo(String accountNumber) {

    Account account = accountRepository.findByAccountNumber(accountNumber);
    if (Objects.isNull(account)) {
      throw new BadRequestDataException("Account with this Account ID does not exist " + accountNumber);
    }

    return account;
  }

  public static Patient checkUser(String idNumber) {

    Patient appUser = restTemplate.getForObject("http://patient-service/patients/get-by-id-number/" + idNumber, Patient.class);
    if (Objects.isNull(appUser)) {
      throw new BadRequestDataException("User with this email does not exist " + idNumber);
    }

    return appUser;
  }


  public static ViewTransactionDto getViewTransactionDto(Transaction transaction, Account account) {
    ViewTransactionDto viewTransactionDto = new ViewTransactionDto();
    viewTransactionDto.setAccount(account);
    viewTransactionDto.setAccountNumber(transaction.getAccountNumber());
    viewTransactionDto.setEmail(transaction.getIdNumber());
    viewTransactionDto.setPatient(CheckDetailsUtil.checkUser(transaction.getIdNumber()));


    viewTransactionDto.setAmount(transaction.getAmount());

    viewTransactionDto.setTransactionDateTime(transaction.getTransactionDate());
    viewTransactionDto.setTransactionId(transaction.getId());

    return viewTransactionDto;
  }

  public static Transaction getTransaction(CreateTransactionDto transactionDto, Account sourceAccount) {
    // create transaction if both accounts exist
    Transaction transaction = new Transaction();

    transaction.setTransactionDate(LocalDateTime.now());
    transaction.setIdNumber(transactionDto.getIdNumber());
    transaction.setAmount(transactionDto.getAmount());


    transaction.setAccountNumber(sourceAccount.getAccountNumber());
    return transaction;
  }

  public static Transaction saveTransaction(Transaction transaction) {
    // create transaction if both accounts exist
    log.info("Register Transaction :{}", transaction);
    return transactionRepository.save(transaction);

  }

  public static Account updateAccount(Account account) {
    // create transaction if both accounts exist
    log.info("Register Transaction :{}", account);
    return accountRepository.save(account);

  }


}
