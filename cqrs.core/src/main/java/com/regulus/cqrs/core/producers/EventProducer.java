package com.regulus.cqrs.core.producers;

import com.regulus.cqrs.core.events.BaseEvent;

public interface EventProducer {

    void produce(String topic, BaseEvent baseEvent);
}
