package com.bank.Transaction.entity;

import com.bank.Transaction.enums.Transaction_Status;
import com.bank.Transaction.enums.Transaction_Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromAccount;
    private String toAccount;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Transaction_Type transactionType;
    @Enumerated(EnumType.STRING)
    private Transaction_Status transactionStatus;

    private LocalDateTime transactionDate;
}
