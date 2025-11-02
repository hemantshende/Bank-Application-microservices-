package com.bank.Transaction.services;

import com.bank.Transaction.dtos.CreditRequestDTO;
import com.bank.Transaction.dtos.DebitRequestDTO;
import com.bank.Transaction.dtos.RequestDTO;
import com.bank.Transaction.dtos.ResponseDTO;
import com.bank.Transaction.entity.Transaction;
import com.bank.Transaction.enums.Transaction_Status;
import com.bank.Transaction.enums.Transaction_Type;
import com.bank.Transaction.feignClients.AccountClient;
import com.bank.Transaction.repos.TransactionRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private AccountClient accountClient;

    private static final String ACCOUNT_SERVICE = "accountService";


    @Transactional
    @CircuitBreaker(name = ACCOUNT_SERVICE, fallbackMethod = "accountServiceFallback")
    public ResponseDTO transferMoney(RequestDTO request){
        try {
            //step-1 ---->create random transaction id
            String transactionId= UUID.randomUUID().toString();    //random transaction id

            //debit from fromAccount
            DebitRequestDTO debitRequestDTO=DebitRequestDTO.builder()
                    .accountNumber(request.getFromAccountNumber())
                    .amount(request.getAmount())
                    .transactionId(transactionId)
                    .build();
            accountClient.debit(debitRequestDTO);   //uses feign client for DEBIT...can see feignClient pkg...

            //credit to toAccount
            CreditRequestDTO creditRequestDTO=CreditRequestDTO.builder()
                    .transactionId(transactionId)
                    .accountNumber(request.getToAccountNumber())
                    .amount(request.getAmount())
                    .build();
            accountClient.credit(creditRequestDTO);  //uses feign client for CREDIT...can see feignClient pkg...


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
            responseDTO.setAmount(txn.getAmount());
            responseDTO.setTransactionDate(txn.getTransactionDate());
            responseDTO.setStatus(txn.getTransactionStatus());
            responseDTO.setMessage("transfer successfully");

            return responseDTO;
        } catch (FeignException.FeignClientException e) {
            String errorMessage=e.getMessage();
            ResponseDTO failed = new ResponseDTO();
            failed.setTransactionId(null);
            failed.setFromAccount(request.getFromAccountNumber());
            failed.setToAccount(request.getToAccountNumber());
            failed.setAmount(request.getAmount());
            failed.setTransactionDate(LocalDateTime.now());
            failed.setStatus(Transaction_Status.FAILED);
            failed.setMessage("Transaction failed : " + errorMessage);
            return failed;
        } catch (Exception e) {
            String errorMessage=e.getMessage();
            ResponseDTO failed = new ResponseDTO();
            failed.setTransactionId(null);
            failed.setFromAccount(request.getFromAccountNumber());
            failed.setToAccount(request.getToAccountNumber());
            failed.setAmount(request.getAmount());
            failed.setTransactionDate(LocalDateTime.now());
            failed.setStatus(Transaction_Status.FAILED);
            failed.setMessage("Transaction failed : " + e.getMessage());
            return failed;
        }
    }

    public ResponseDTO accountServiceFallback(RequestDTO request, Throwable t) {
        ResponseDTO fallback = new ResponseDTO();
        fallback.setTransactionId(null);
        fallback.setFromAccount(request.getFromAccountNumber());
        fallback.setToAccount(request.getToAccountNumber());
        fallback.setAmount(request.getAmount());
        fallback.setStatus(Transaction_Status.FAILED);
        fallback.setTransactionDate(LocalDateTime.now());
        fallback.setMessage("⚠️ Account Service unavailable — please try again later.");
        return fallback;
    }
}
