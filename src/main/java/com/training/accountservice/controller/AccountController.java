package com.training.accountservice.controller;

import com.training.accountservice.domin.Account;
import com.training.accountservice.dto.CreateAccountRequestDto;
import com.training.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/accounts")
    public ResponseEntity<Void> saveAccount(@RequestBody CreateAccountRequestDto createAccountRequestDto){
       String accountId = accountService.saveAccount(createAccountRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", UriComponentsBuilder.fromPath("/api/account/").pathSegment(accountId).toUriString());
       return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping("/api/accounts")
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }
}
