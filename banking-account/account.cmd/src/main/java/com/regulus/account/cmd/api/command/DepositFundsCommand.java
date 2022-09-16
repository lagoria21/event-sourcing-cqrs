package com.regulus.account.cmd.api.command;

import com.regulus.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DepositFundsCommand extends BaseCommand {

    private double amount;
}
