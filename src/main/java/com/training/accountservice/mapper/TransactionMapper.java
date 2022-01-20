package com.training.accountservice.mapper;

import com.training.accountservice.domain.Transaction;
import com.training.accountservice.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    public Transaction toTransaction(TransactionDto transactionDto);
}
