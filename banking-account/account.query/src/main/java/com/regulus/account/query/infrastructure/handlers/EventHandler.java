package com.regulus.account.query.infrastructure.handlers;

import com.regulus.account.common.events.AccountClosedEvent;
import com.regulus.account.common.events.AccountOpenedEvent;
import com.regulus.account.common.events.FundsDepositedEvent;
import com.regulus.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
