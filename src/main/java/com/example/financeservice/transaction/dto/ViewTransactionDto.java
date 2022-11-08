package com.example.financeservice.transaction.dto;


import com.example.financeservice.model.Account;
import com.example.financeservice.model.AppUser;
import com.example.financeservice.model.Patient;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ViewTransactionDto extends CreateTransactionDto implements Serializable {

  private Account account;
  private String email;
  private Patient patient;
  private LocalDateTime transactionDateTime;
  private Long transactionId;

}
