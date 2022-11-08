package com.example.financeservice.account.dto;


import com.example.financeservice.common.AccountType;
import com.example.financeservice.common.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CreateAccountDto extends BaseDto implements Serializable {

  private AccountType accountType;
  private String accountNumber;
  private String idNumber;
  private String currency;
  private double accountBalance;
  private LocalDate creationDate;
}
