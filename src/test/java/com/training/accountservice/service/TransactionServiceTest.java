package com.training.accountservice.service;

import com.training.accountservice.domain.Transaction;
import com.training.accountservice.domin.Account;
import com.training.accountservice.dto.TransactionDto;
import com.training.accountservice.dto.TransactionNotificationDto;
import com.training.accountservice.mapper.TransactionMapper;
import com.training.accountservice.repository.AccountRepository;
import com.training.accountservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    TransactionMapper transactionMapper;

    @Mock
    StreamBridge streamBridge;

    TransactionService trasactionService;

    @Captor
    ArgumentCaptor<Account> accountArgumentCaptor;

    @Captor
    ArgumentCaptor<Message> messageArgumentCaptor;

    @BeforeEach
    public void setup(){
        trasactionService = new TransactionService(transactionRepository,accountRepository,transactionMapper,streamBridge);
    }

    @Test
    void shouldSaveTransaction(){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.ONE);
        when(accountRepository.findById(any())).thenReturn(
                Optional.of(Account
                        .builder()
                        .accountId("ACC12345")
                        .balance(BigDecimal.ZERO)
                        .build()));
        when(transactionRepository.save(any())).thenReturn(Transaction.builder().transactionId(1L).build());
        Long transactionId = trasactionService.saveTransaction(transactionDto);
        assertThat(transactionId).isEqualTo(1L);
    }

    @Test
    void shouldUpdateBalanceBeforeSavingTransaction(){
        TransactionDto transactionDto = TransactionDto.builder().accountId("ACC12345").amount(BigDecimal.ONE).build();
        when(accountRepository.findById(any())).thenReturn(
                Optional.of(Account
                        .builder()
                        .accountId("ACC12345")
                        .balance(BigDecimal.ZERO)
                        .build()));
        when(transactionRepository.save(any())).thenReturn(
                Transaction
                        .builder()
                        .transactionId(1L)
                        .build());
        Long transactionId = trasactionService.saveTransaction(transactionDto);
        assertThat(transactionId).isEqualTo(1L);
        verify(accountRepository,times(1)).save(accountArgumentCaptor.capture());
        Account account = accountArgumentCaptor.getValue();
        assertThat(account.getAccountId()).isEqualTo("ACC12345");
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ONE);

    }

    @Disabled
    @Test
    void shouldSendAMessageToNotificationService(){
        TransactionDto transactionDto = TransactionDto.builder().accountId("ACC12345").amount(BigDecimal.ONE).build();
        when(accountRepository.findById(any())).thenReturn(
                Optional.of(Account
                        .builder()
                        .accountId("ACC12345")
                        .balance(BigDecimal.ZERO)
                        .build()));
        when(transactionRepository.save(any())).thenReturn(
                Transaction
                        .builder()
                        .transactionId(1L)
                        .build());
        Long transactionId = trasactionService.saveTransaction(transactionDto);
        verify(streamBridge,times(1)).send(anyString(),messageArgumentCaptor.capture());
        Message<TransactionNotificationDto> message = messageArgumentCaptor.getValue();
        assertThat(message.getPayload()).isNotNull();
        assertThat(message.getPayload().getTransactionId()).isEqualTo(1L);
    }


}
