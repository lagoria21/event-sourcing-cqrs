package com.regulus.account.cmd.api.controllers;

import com.regulus.account.cmd.api.command.OpenAccountCommand;
import com.regulus.account.cmd.api.dto.OpenAccountResponse;
import com.regulus.account.common.dto.BaseResponse;
import com.regulus.cqrs.core.infrastructure.CommandDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/open-bank-account")
@Slf4j
public class OpenAccountController {

    private final CommandDispatcher commandDispatcher;

    public OpenAccountController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command){
        var id = UUID.randomUUID().toString();
        command.setId(id);

        try {
            commandDispatcher.send(command);
            return new ResponseEntity<>(new OpenAccountResponse("La cuenta se creo exitosamente", id), HttpStatus.CREATED);
        }catch (IllegalStateException e){
            log.error("No se puedo generar la cuenta del banco {0}", e.toString());
            return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            var safeErrorMessage = MessageFormat.format("Errores de proceso {0}", id);
            log.error(safeErrorMessage);
            return new ResponseEntity<>(new OpenAccountResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
