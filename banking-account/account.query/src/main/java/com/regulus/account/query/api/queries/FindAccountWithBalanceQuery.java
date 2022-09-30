package com.regulus.account.query.api.queries;

import com.regulus.account.query.api.dto.EqualityType;
import com.regulus.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {

    private double balance;
    private EqualityType equalityType;
}
