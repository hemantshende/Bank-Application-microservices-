package com.bank.Transaction.controller;

import com.bank.Transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/transfer")
    public TransferResponseDTO transferMoney(@RequestBody TransferRequestDTO request) {
        return service.transferMoney(request);
    }
}
