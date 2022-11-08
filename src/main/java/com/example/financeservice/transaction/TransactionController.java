package com.example.financeservice.transaction;//package com.startaptradingapi.startaptradingapi.finance.transaction;

import com.example.financeservice.common.TransactionType;
import com.example.financeservice.exeption.BadRequestDataException;
import com.example.financeservice.model.Patient;
import com.example.financeservice.transaction.dto.CreateTransactionDto;
import com.example.financeservice.transaction.dto.ViewTransactionDto;
import com.example.financeservice.transaction.service.TransactionProcessorFactory;
import com.example.financeservice.transaction.service.TransactionService;
import com.example.financeservice.utils.CheckDetailsUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;


@AllArgsConstructor
@Slf4j
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    private RestTemplate restTemplate;


    @SneakyThrows
    @PostMapping(value = "/transactions/{transactiontypeid}")

    public ResponseEntity<ViewTransactionDto> createTransaction(@RequestBody CreateTransactionDto createTransactionDto, @PathVariable("transactiontypeid") int transactionTypeId, Authentication authentication) throws IOException {

        log.info("New Transaction : {} ", createTransactionDto);

        //TODO check using security token, userid, or login key
        String username = authentication.getName();

//        Optional<AppUser> appUser = appUserService.getUserByEmail(createTransactionDto.getEmail());
        Patient appUser = restTemplate.getForObject("http://patient-service/patients/get-by-id-number/" + username, Patient.class);
        if (appUser != null) {
            ViewTransactionDto viewTransactionDto = TransactionProcessorFactory.getTransactionType(transactionTypeId).createTransaction(createTransactionDto);
            return new ResponseEntity<>(viewTransactionDto, HttpStatus.CREATED);
        } else {
            throw new BadRequestDataException(" User does not exist");
        }

    }

    @GetMapping(value = "/transactions/{transactiontypeid}/{email}")
    public ResponseEntity<List<ViewTransactionDto>> getTransactionHistoryByType(@PathVariable("email") String email,
                                                                                @PathVariable("transactiontypeid") int transactionTypeId) throws IOException {

        log.info("Getting transaction of type :" + transactionTypeId + "{} for this user email" + email);

        //TODO check using security token, userid, or login key

        CheckDetailsUtil.checkUser(email);
        List<ViewTransactionDto> viewTransactionDtoList = transactionService.getTransactionHistoryByType(TransactionType.getTransactionType(transactionTypeId), email);
        return new ResponseEntity<>(viewTransactionDtoList, HttpStatus.CREATED);

    }

    @GetMapping(value = "/transactions/users/{email}")
    public ResponseEntity<List<ViewTransactionDto>> getAllTransactionHistory(@PathVariable("email") String email) throws IOException {

        log.info("Getting transaction history {} for this user" + email);

        //TODO check using security token, userid, or login key

        CheckDetailsUtil.checkUser(email);
        List<ViewTransactionDto> viewTransactionDtoList = transactionService.getAllTransactions(email);
        return new ResponseEntity<>(viewTransactionDtoList, HttpStatus.CREATED);

    }


}
