package com.adiwave.reactorexercises.taskscheduler;

import reactor.core.publisher.Mono;

public interface ReactiveTask {
    String id();
    Mono<Void> execute(TaskContext context);
}
