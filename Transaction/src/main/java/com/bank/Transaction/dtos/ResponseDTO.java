package com.bank.Transaction.dtos;

import com.bank.Transaction.enums.Transaction_Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Long transactionId;
    private String fromAccount;
    private String toAccount;
    private Double amount;
    private Transaction_Status status;
    private LocalDateTime transactionDate;
    private String message;
}
