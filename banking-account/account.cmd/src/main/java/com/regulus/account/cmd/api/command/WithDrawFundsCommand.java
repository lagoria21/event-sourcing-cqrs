package com.regulus.account.cmd.api.command;

import com.regulus.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithDrawFundsCommand extends BaseCommand {

    private double amount;

}
