package com.regulus.account.cmd.api.command;

import com.regulus.account.common.dto.AccountType;
import com.regulus.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;

}
