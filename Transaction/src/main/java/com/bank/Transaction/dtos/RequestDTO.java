package com.bank.Transaction.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private Double amount;
}
