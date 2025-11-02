package com.bank.account_service.Controller;

import com.bank.account_service.dtos.*;
import com.bank.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {

    @Autowired
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //--------------------------create Account---------------------------//
    @PostMapping("/createAccount")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        AccountResponse response=accountService.createAccount(request,userName);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //----------------------Debit Money----------------//
    @PostMapping("/debit")
    public ResponseEntity<TransactionResponseDTO> debitBalance(@RequestBody DebitRequestDTO request) {
        TransactionResponseDTO response = accountService.debitBalance(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //----------------------Credit Money---------------//
    @PostMapping("/credit")
    public ResponseEntity<TransactionResponseDTO> creditBalance(@RequestBody CreditRequestDTO request) {
        TransactionResponseDTO response = accountService.creditBalance(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AccountResponse> getAccountByID(@PathVariable Long id){
        AccountResponse response=accountService.getAccountByID(id);
        return  new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByAccountNumber/{accNumber}")
    public ResponseEntity<AccountResponse> getByAccountNumber(@PathVariable String accNumber) {
        AccountResponse response = accountService.getByAccountNumber(accNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<Page<AccountResponse>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction){
       Page<AccountResponse> pages=accountService.list(page, size, sortBy, direction);
        return new ResponseEntity<>(pages,HttpStatus.OK);
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id,@RequestBody AccountRequest request){
        AccountResponse response=accountService.update(id,request);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
