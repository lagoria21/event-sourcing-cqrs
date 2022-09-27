package com.regulus.account.cmd;

import com.regulus.account.cmd.api.command.*;
import com.regulus.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan({"com.regulus.account.cmd", "com.regulus.account.cmd.domain,com.regulus.cqrs.core.infrastructure", "com.regulus.account.cmd.domain"})
public class CommandApplication {


	@Autowired
	private CommandDispatcher dispatcher;

	@Autowired
	private CommandHandler commandHandler;

	public static void main(String[] args) {
		SpringApplication.run(CommandApplication.class, args);
	}

	@PostConstruct
	public void registerHandlers(){
		dispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
		dispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
		dispatcher.registerHandler(WithDrawFundsCommand.class, commandHandler::handle);
		dispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
	}
}
