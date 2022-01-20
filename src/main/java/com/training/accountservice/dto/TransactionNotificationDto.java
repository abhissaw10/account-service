package com.training.accountservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TransactionNotificationDto {
    private Long transactionId;
}
