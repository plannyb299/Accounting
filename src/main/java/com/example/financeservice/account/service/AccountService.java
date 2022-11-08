package com.example.financeservice.account.service;


import com.example.financeservice.account.dto.CreateAccountDto;
import com.example.financeservice.account.dto.ViewAccountDto;
import com.example.financeservice.model.Account;
import com.example.financeservice.model.Patient;

import java.util.List;

public interface AccountService {

  ViewAccountDto openAccount(CreateAccountDto createAccountDto);

  Account checkAccount(Patient appUser);

  ViewAccountDto findAccountById(Long id);

  ViewAccountDto findByAccountNumber(String accountNumber);

  List<ViewAccountDto> findAllAccounts();
}
