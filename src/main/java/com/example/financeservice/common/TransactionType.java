package com.example.financeservice.common;


import com.example.financeservice.exeption.BadRequestDataException;


public enum TransactionType {

  DEPOSIT(1),
  WITHDRAW(2),
  SEND_MONEY(3);

  private final int transactionTypeId;

  TransactionType(int transactionTypeId) {
    this.transactionTypeId = transactionTypeId;
  }

  public int getTransactionTypeId() {
    return transactionTypeId;
  }

  public static TransactionType getTransactionType(int transactionTypeId) {
    if (transactionTypeId == 1) {
      return TransactionType.DEPOSIT;
    } else if (transactionTypeId == 2) {
      return TransactionType.WITHDRAW;
    } else if (transactionTypeId == 3) {
      return TransactionType.SEND_MONEY;
    } else {

      throw new BadRequestDataException("Transaction Type given is not known for this operation" + transactionTypeId);
    }
  }
}
