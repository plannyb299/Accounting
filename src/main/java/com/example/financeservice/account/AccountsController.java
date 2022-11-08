package com.example.financeservice.account;


import com.example.financeservice.account.dto.CreateAccountDto;
import com.example.financeservice.account.dto.ViewAccountDto;
import com.example.financeservice.account.service.AccountService;
import com.example.financeservice.exeption.BadRequestDataException;
import com.example.financeservice.model.Patient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AccountsController {

  private final AccountService accountService;
  //  private final UserService appUserService;
  @Autowired
  private final RestTemplate restTemplate;


  @SneakyThrows
  @PostMapping(value = "api/v1/accounts/create")

  public ResponseEntity<ViewAccountDto> openAccount(@RequestBody CreateAccountDto accountDto) throws IOException {

    log.info("New Account Registration : {} ", accountDto);

    Patient appUser = restTemplate.getForObject("http://patient-service/patients/get-by-id-number/" + accountDto.getIdNumber(), Patient.class);

//    AppUser appUser = appUserService.findUserByEmail(accountDto.getEmail());
    if (!Objects.isNull(appUser)) {
      final ViewAccountDto createAccountDto = accountService.openAccount(accountDto);
      return new ResponseEntity<>(createAccountDto, HttpStatus.CREATED);
    } else {
      throw new BadRequestDataException("There is no user with email " + accountDto.getIdNumber());
    }

  }

}
