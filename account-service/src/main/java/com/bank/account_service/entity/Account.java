package com.bank.account_service.entity;

import com.bank.account_service.Enum.Account_Type;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="account")
@Builder
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accHolderName;

    @Column(nullable = false)
    private String ownerUsername;

    @Column(nullable = false)
    private Double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Account_Type accountType;
}
