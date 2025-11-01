package com.bank.account_service.Controller;

import com.bank.account_service.dtos.AccountRequest;
import com.bank.account_service.dtos.AccountResponse;
import com.bank.account_service.entity.Account;
import com.bank.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")

public class AccountController {

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request){
        AccountResponse response=accountService.createAccount(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AccountResponse> getAccountByID(@PathVariable Long id){
        AccountResponse response=accountService.getAccountByID(id);
        return  new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getPage")
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
    public ResponseEntity<Void> delete(Long id){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
