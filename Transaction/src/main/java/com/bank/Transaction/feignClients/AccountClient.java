package com.bank.Transaction.feignClients;

import com.bank.Transaction.dtos.CreditRequestDTO;
import com.bank.Transaction.dtos.DebitRequestDTO;
import com.bank.Transaction.dtos.TransactionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service" , url = "http://localhost:8081")
public interface AccountClient {

    @PostMapping("/api/accounts/debit")
    TransactionResponseDTO debit(@RequestBody DebitRequestDTO requestDTO);

    @PostMapping("/api/accounts/debit")
    TransactionResponseDTO credit(@RequestBody CreditRequestDTO requestDTO);
}
