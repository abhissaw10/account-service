package com.training.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.accountservice.domain.AccountType;
import com.training.accountservice.dto.CreateAccountRequestDto;
import com.training.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @Test
    void shouldCreateAccount() throws Exception {
        when(accountService.saveAccount(any())).thenReturn("ACC12345");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(CreateAccountRequestDto
                        .builder()
                        .customerId("CUST12345")
                        .accountType(AccountType.SAVINGS)
                        .build())))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
    }

}
