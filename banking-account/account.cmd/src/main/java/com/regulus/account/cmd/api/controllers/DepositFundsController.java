package com.regulus.account.cmd.api.controllers;


import com.regulus.account.cmd.api.command.DepositFundsCommand;
import com.regulus.account.cmd.api.dto.OpenAccountResponse;
import com.regulus.account.common.dto.BaseResponse;
import com.regulus.cqrs.core.exceptions.AggregateNotFoundException;
import com.regulus.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = "/api/v1/deposit-funds")
@Slf4j
public class DepositFundsController {

    private final CommandDispatcher commandDispatcher;

    public DepositFundsController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                     @RequestBody DepositFundsCommand command) {

        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposito exitoso"), HttpStatus.OK);
        } catch (IllegalStateException | AggregateNotFoundException e){
            var safeErrorMessage = MessageFormat.format("request invalido {0}", e.toString());
            log.error(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception ex){
            var safeErrorMessage = MessageFormat.format("Errores mientras se procesaba el request {id}", id);
            log.error(safeErrorMessage);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
