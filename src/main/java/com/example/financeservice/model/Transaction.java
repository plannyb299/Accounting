package com.example.financeservice.model;


import com.example.financeservice.common.BaseEntity;
import com.example.financeservice.common.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions", indexes = {@Index(name = "indx_transactions", columnList = "id", unique = true)})
@Getter
@Setter
@ToString
public class Transaction extends BaseEntity {

  @Column(name = "transactiontype", nullable = false)
  private TransactionType transactionType;

  @Column(name = "amount", nullable = false)
  private double amount;

  @Column(name = "transactiondate", nullable = false)
  private LocalDateTime transactionDate;

  @ManyToOne
  @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "FK_TRANSACTION"))
  @JsonIgnoreProperties
  @Transient
  private Account account;

  @Column(name = "sourceaccountNo", nullable = true)
  private String accountNumber;

  @Column(name = "customer_idNumber", nullable = false)
  private String idNumber;


}
