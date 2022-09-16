package com.regulus.cqrs.core.commands;

@FunctionalInterface
public interface CommandHandlerMethod <T extends BaseCommand>{
    void handle(T commands);
}
