package com.bank.Transaction.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDTO {
    private String accountNumber;
    private Double amount;
    private String transactionId;
}