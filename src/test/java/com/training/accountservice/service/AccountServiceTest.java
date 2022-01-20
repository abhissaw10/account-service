package com.training.accountservice.service;

import com.training.accountservice.AccountMapper;
import com.training.accountservice.AccountMapperImpl;
import com.training.accountservice.domain.AccountType;
import com.training.accountservice.domin.Account;
import com.training.accountservice.dto.CreateAccountRequestDto;
import com.training.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Import(AccountMapperImpl.class)
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountService accountService;

    @Autowired
    AccountMapper accountMapper;

    @BeforeEach
    public void beforeEach(){
        accountService = new AccountService(accountRepository,accountMapper);
    }

   @Test
    void shouldSaveAccount(){
        when(accountRepository.save(any())).thenReturn(Account.builder().accountId("ACC1234567").build());
       String accountId = accountService.saveAccount(defaultAccountDto());
       verify(accountRepository,times(1)).save(any());
   }

   private CreateAccountRequestDto defaultAccountDto(){
       CreateAccountRequestDto createAccountRequestDto = new CreateAccountRequestDto();
       createAccountRequestDto.setCustomerId("CUST12345");
       createAccountRequestDto.setAccountType(AccountType.SAVINGS);
       return createAccountRequestDto;
   }


}
