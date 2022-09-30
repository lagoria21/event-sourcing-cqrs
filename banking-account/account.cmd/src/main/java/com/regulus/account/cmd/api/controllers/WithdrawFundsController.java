package com.regulus.account.cmd.api.controllers;


import com.regulus.account.cmd.api.command.WithDrawFundsCommand;
import com.regulus.account.common.dto.BaseResponse;
import com.regulus.cqrs.core.exceptions.AggregateNotFoundException;
import com.regulus.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequestMapping(path = "/api/v1/with-draw-funds")
@Slf4j
public class WithdrawFundsController {

    private final CommandDispatcher commandDispatcher;


    public WithdrawFundsController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> withDrawFunds(@PathVariable(value = "id") String id,
                                                      @RequestBody WithDrawFundsCommand command){

        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Retido de dinero exitoso"), HttpStatus.OK);
        }catch (IllegalStateException | AggregateNotFoundException e){
            log.warn("El cliente envio un request con errores {0}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            var safeErrorMessage = MessageFormat.format("Errores mientras procesaba el request {id}", id);
            return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
