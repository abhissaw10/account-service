package com.training.accountservice.dto;

import com.training.accountservice.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequestDto {
    private String customerId;
    private AccountType accountType;
}
