package com.regulus.account.cmd.api.controllers;


import com.regulus.account.cmd.api.command.CloseAccountCommand;
import com.regulus.account.common.dto.BaseResponse;
import com.regulus.cqrs.core.exceptions.AggregateNotFoundException;
import com.regulus.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = "/api/v1/close-bank-account")
@Slf4j
public class CloseAccountController {

    private final CommandDispatcher commandDispatcher;

    public CloseAccountController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> closeAccount(@PathVariable(value = "id") String id){

        try {
            commandDispatcher.send(new CloseAccountCommand(id));
            return new ResponseEntity<>(new BaseResponse("Se cerro la cuenta bancaria exitosamente"), HttpStatus.OK);
        }catch(IllegalStateException | AggregateNotFoundException e){
            log.warn(MessageFormat.format("El cliente envio un request con errores {0} ", e.toString()));
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            var safeErrorMessage = MessageFormat.format("Errores mientras procesaba el request {id}", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
