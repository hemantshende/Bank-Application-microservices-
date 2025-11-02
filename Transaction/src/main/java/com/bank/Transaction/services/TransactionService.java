package com.bank.Transaction.services;

import com.bank.Transaction.dtos.RequestDTO;
import com.bank.Transaction.dtos.ResponseDTO;
import com.bank.Transaction.entity.Transaction;
import com.bank.Transaction.enums.Transaction_Status;
import com.bank.Transaction.enums.Transaction_Type;
import com.bank.Transaction.repos.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository){
        this.repository=repository;
    }

    public ResponseDTO transferMoney(RequestDTO request){

        //TODO
        //
        Transaction txn = Transaction.builder()
                .fromAccount(request.getFromAccountNumber())
                .toAccount(request.getToAccountNumber())
                .amount(request.getAmount())
                .transactionType(Transaction_Type.TRANSFER)
                .transactionDate(LocalDateTime.now())
                .transactionStatus(Transaction_Status.SUCCESS)
                .build();

        repository.save(txn);  //save in DB

        ResponseDTO responseDTO=new ResponseDTO();
        responseDTO.setTransactionId(txn.getId());
        responseDTO.setFromAccount(txn.getFromAccount());
        responseDTO.setToAccount(txn.getToAccount());
        responseDTO.setTransactionDate(txn.getTransactionDate());
        responseDTO.setStatus(txn.getTransactionStatus());
        responseDTO.setMessage("transfer successfully");

        return responseDTO;


    }
}
