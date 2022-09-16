package com.regulus.account.cmd.api.command;

import com.regulus.cqrs.core.commands.BaseCommand;


public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id){
        super(id);
    }

}
