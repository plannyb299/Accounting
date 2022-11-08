package com.example.financeservice.account.service;


import com.example.financeservice.account.dto.CreateAccountDto;
import com.example.financeservice.account.dto.ViewAccountDto;
import com.example.financeservice.account.repository.AccountRepository;
import com.example.financeservice.exeption.BadRequestDataException;
import com.example.financeservice.model.Account;
import com.example.financeservice.model.Patient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  //  private final UserRepository appUserRepository;
  private final ModelMapper mapper;

  @Autowired
  private final RestTemplate restTemplate;


  @Override
  public ViewAccountDto openAccount(CreateAccountDto createAccountDto) {

    log.info("Register Account :{}", createAccountDto);
    Patient appUser = restTemplate.getForObject("http://patient-service/patients/get-by-id-number/" + createAccountDto.getIdNumber(), Patient.class);
//    AppUser appUser = checkUserByEmail(createAccountDto.getEmail());
    if (appUser == null) {
      throw new BadRequestDataException("User with this Email" + createAccountDto.getIdNumber() + " not found");
    }

    Account account = createAcccount(createAccountDto);
    accountRepository.save(account);

    //create savings account if current account >1000

    return getViewAccountDto(account);
  }

  @Override
  public Account checkAccount(Patient appUser) {
    return accountRepository.findByPatient(appUser);
  }


  private ViewAccountDto getViewAccountDto(Account account) {
    ViewAccountDto viewAccountDto = mapper.map(account, ViewAccountDto.class);

    return viewAccountDto;
  }

  @Override
  public ViewAccountDto findAccountById(Long id) {

    log.info("Getting account :{}", id);
    Optional<Account> account = accountRepository.findById(id);

    if (account.isPresent()) {
      return getViewAccountDto(account.get());
    } else {
      throw new IllegalArgumentException("Account is not found with ID " + id);
    }
  }

  @Override
  public ViewAccountDto findByAccountNumber(String accountNumber) {

    Account account = accountRepository.findByAccountNumber(accountNumber);
    if (!Objects.isNull(account)) {
      return getViewAccountDto(account);
    } else {
      throw new IllegalArgumentException("Account is not found with Id" + accountNumber);
    }
  }


  @Override
  public List<ViewAccountDto> findAllAccounts() {

    List<Account> accountList = accountRepository.findAll();

    return createViewAccountDtoList(accountList);

  }


  protected Account createAcccount(CreateAccountDto createAccountDto) {
    Account account = mapper.map(createAccountDto, Account.class);
    return account;

  }

  protected Patient checkUserByIdNumber(String idNumber) {

    Patient appUser = restTemplate.getForObject("http://patient-service/patients/get-by-id-number/" + idNumber, Patient.class);

    return appUser;
  }

  public List<ViewAccountDto> createViewAccountDtoList(List<Account> accountList) {

    if (accountList.isEmpty()) {
      return new ArrayList<>();
    }
    return accountList.stream().map(this::getViewAccountDto).collect(Collectors.toList());
  }
}
