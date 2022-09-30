package com.regulus.cqrs.core.infrastructure;

import com.regulus.cqrs.core.domain.BaseEntity;
import com.regulus.cqrs.core.queries.BaseQuery;
import com.regulus.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity> List<U> send(BaseQuery query);
}
