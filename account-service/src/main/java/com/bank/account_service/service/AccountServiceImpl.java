package com.bank.account_service.service;

import com.bank.account_service.dtos.AccountRequest;
import com.bank.account_service.dtos.AccountResponse;
import com.bank.account_service.entity.Account;
import com.bank.account_service.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public AccountResponse createAccount(AccountRequest request, String userName) {
        Account createAccount = toEntity(request);
        createAccount.setOwnerUsername(userName);
        createAccount = repository.save(createAccount);
        return toResponse(createAccount);
    }


    @Override
    public AccountResponse getAccountByID(Long id) {
        Account account=repository.findById(id).orElseThrow(()->new RuntimeException("acc not found"));
        AccountResponse response=toResponse(account);
        return response;
    }

    @Override
    public Page<AccountResponse> list(int pageNumber, int size, String sortBy, String direction) {
        Sort sort=Sort.by(Sort.Direction.ASC);
        Pageable pageable= PageRequest.of(pageNumber,size,sort);
        Page<Account> listOfAccount=repository.findAll(pageable);
        return listOfAccount.map(this::toResponse);
    }

    @Override
    public AccountResponse update(Long id, AccountRequest request) {
        Account existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Account not found: " + id));
        if (request.getAccountHolderName() != null) existing.setAccHolderName(request.getAccountHolderName());
        if (request.getAccountType() != null) existing.setAccountType(request.getAccountType());
        if (request.getBalance() != null) existing.setBalance(request.getBalance());
        // do not allow changing account number here; could be allowed if needed
        existing = repository.save(existing);
        return toResponse(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private AccountResponse toResponse(Account a) {
        return AccountResponse.builder()
                .id(a.getId())
                .accountNumber(a.getAccountNumber())
                .accountHolderName(a.getAccHolderName())
                .balance(a.getBalance())
                .ownerUsername(a.getOwnerUsername())
                .accountType(a.getAccountType())
                .build();
    }

    private Account toEntity(AccountRequest r) {
        return Account.builder()
                .accountNumber(r.getAccountNumber())
                .accHolderName(r.getAccountHolderName())
                .balance(r.getBalance() == null ? 0.0 : r.getBalance())
                .accountType(r.getAccountType())
                .build();
    }

}
