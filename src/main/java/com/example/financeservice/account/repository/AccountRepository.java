package com.example.financeservice.account.repository;


import com.example.financeservice.model.Account;
import com.example.financeservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Account findByAccountNumber(String accountId);

  Optional<Account> findById(Long Id);

  Account findByPatient(Patient appUser);


}
