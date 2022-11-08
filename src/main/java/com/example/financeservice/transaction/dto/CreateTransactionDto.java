package com.example.financeservice.transaction.dto;


import com.example.financeservice.common.BaseDto;
import com.example.financeservice.common.TransactionType;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateTransactionDto extends BaseDto implements Serializable {


  private TransactionType transactionType;
  private double amount;
  private String accountNumber;

  private String idNumber;

}
