package com.bank.Transaction.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private Double balance;
    private String accountType;
    private String ownerUsername;
}