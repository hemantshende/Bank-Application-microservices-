package com.bank.account_service.dtos;

import com.bank.account_service.Enum.Account_Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private Double balance;
    private Account_Type accountType;
    private String ownerUsername;
}
