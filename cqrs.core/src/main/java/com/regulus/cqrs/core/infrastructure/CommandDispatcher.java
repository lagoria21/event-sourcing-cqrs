package com.regulus.cqrs.core.infrastructure;

import com.regulus.cqrs.core.commands.BaseCommand;
import com.regulus.cqrs.core.commands.CommandHandlerMethod;



public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);

}
