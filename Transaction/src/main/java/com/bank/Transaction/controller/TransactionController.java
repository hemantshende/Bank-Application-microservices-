package com.bank.Transaction.controller;

import com.bank.Transaction.dtos.RequestDTO;
import com.bank.Transaction.dtos.ResponseDTO;
import com.bank.Transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/transfer")
    public ResponseDTO transferMoney(@RequestBody RequestDTO request) {
        return service.transferMoney(request);
    }
}
