package com.example.financeservice.common;

public enum AccountType {


  CURRENT(1),
  SAVINGS_ACCOUNT(2);

  private final int accountTypeId;
  AccountType(int accountTypeId) {
    this.accountTypeId = accountTypeId;
  }

  public int getAccountTypeId() {
    return accountTypeId;
  }
}
