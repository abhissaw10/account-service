package com.training.accountservice.service;

import com.training.accountservice.AccountMapper;
import com.training.accountservice.domin.Account;
import com.training.accountservice.dto.CreateAccountRequestDto;
import com.training.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    public String saveAccount(CreateAccountRequestDto createAccountRequestDto) {
        return accountRepository.save(accountMapper.toAccount(createAccountRequestDto)).getAccountId();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
