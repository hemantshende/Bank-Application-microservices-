package com.bank.account_service.service;

import com.bank.account_service.dtos.AccountRequest;
import com.bank.account_service.dtos.AccountResponse;
import com.bank.account_service.entity.Account;
import org.springframework.data.domain.Page;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    AccountResponse getAccountByID(Long id);
    Page<AccountResponse> list(int page, int size, String sortBy, String direction);
    AccountResponse update(Long id, AccountRequest request);
    void delete(Long id);
}
