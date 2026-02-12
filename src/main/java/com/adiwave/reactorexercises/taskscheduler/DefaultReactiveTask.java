package com.adiwave.reactorexercises.taskscheduler;

import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class DefaultReactiveTask implements ReactiveTask {

    private final String id;
    private final Function<TaskContext, Mono<Void>> action;
    private final ExecutionPolicy executionPolicy;

    private final AtomicBoolean running = new AtomicBoolean(false);

    public DefaultReactiveTask(
            String id,
            Function<TaskContext, Mono<Void>> action,
            ExecutionPolicy executionPolicy
    ) {
        this.id = Objects.requireNonNull(id);
        this.action = Objects.requireNonNull(action);
        this.executionPolicy = Objects.requireNonNull(executionPolicy);
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public Mono<Void> execute(TaskContext context) {

        return Mono.defer(() -> {
            if(!executionPolicy.canStart(running)) {
                return Mono.empty();
            }
            return action.apply(context)
                    .doFinally(signal -> running.set(false));
        });
    }

}
