package com.training.accountservice.service;

import com.training.accountservice.domain.Transaction;
import com.training.accountservice.domin.Account;
import com.training.accountservice.dto.TransactionDto;
import com.training.accountservice.dto.TransactionNotificationDto;
import com.training.accountservice.mapper.TransactionMapper;
import com.training.accountservice.repository.AccountRepository;
import com.training.accountservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    private final StreamBridge streamBridge;

    public Long saveTransaction(TransactionDto transactionDto) {
        Optional<Account> account = accountRepository.findById(transactionDto.getAccountId());
        Account accountEntity = account.orElseThrow(()->new RuntimeException());
        BigDecimal updatedBalance = accountEntity.getBalance().add(transactionDto.getAmount());
        accountEntity.setBalance(updatedBalance);
        accountRepository.save(accountEntity);
        Transaction response = transactionRepository.save(transactionMapper.toTransaction(transactionDto));
        streamBridge.send("transaction-notification-topic", MessageBuilder
                .withPayload(TransactionNotificationDto
                        .builder()
                        .transactionId(response.getTransactionId())
                        .build())
                .build());
        return response.getTransactionId();
    }
}
