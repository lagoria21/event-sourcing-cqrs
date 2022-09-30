package com.regulus.account.query.api.controllers;

import com.regulus.account.query.api.dto.AccountLookupResponse;
import com.regulus.account.query.api.dto.EqualityType;
import com.regulus.account.query.api.queries.FindAccountByHolderQuery;
import com.regulus.account.query.api.queries.FindAccountByIdQuery;
import com.regulus.account.query.api.queries.FindAccountWithBalanceQuery;
import com.regulus.account.query.api.queries.FindAllAccountQuery;
import com.regulus.account.query.domain.BankAccount;
import com.regulus.cqrs.core.infrastructure.QueryDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/bank-account-lookup")
@Slf4j
public class AccountLookupController {
    private final QueryDispatcher queryDispatcher;

    public AccountLookupController(QueryDispatcher queryDispatcher) {
        this.queryDispatcher = queryDispatcher;
    }

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Se realizo la consulta con exito", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            log.error(safeErrorMessage);
            log.error(e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byId/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable(value="id") String id){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Se realizo la consulta con exito", null))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            log.error(safeErrorMessage);
            log.error(e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value="accountHolder") String accountHolder){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Se realizo la consulta con exito", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            log.error(safeErrorMessage);
            log.error(e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping(path = "/withBalance/{equalityType}/{balance}")
    public ResponseEntity<AccountLookupResponse> getAccountWithBalance(
            @PathVariable(value="equalityType") EqualityType equalityType,
            @PathVariable(value="balance") double balance
    )
    {
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountWithBalanceQuery(balance, equalityType));
            if(accounts == null || accounts.size() == 0){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Se realizo la consulta con exito", null))
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(Exception e){
            var safeErrorMessage = "Errores ejecutando la consulta";
            log.error(safeErrorMessage);
            log.error(e.getMessage());
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
