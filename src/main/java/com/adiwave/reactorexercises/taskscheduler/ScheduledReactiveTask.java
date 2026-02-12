package com.adiwave.reactorexercises.taskscheduler;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class ScheduledReactiveTask {

    private final ReactiveTask task;
    private final ScheduledRule rule;

    public ScheduledReactiveTask(ReactiveTask task, ScheduledRule rule) {
        this.task = task;
        this.rule = rule;
    }

    public Mono<Void> schedule() {
        return Mono.defer(() -> triggerNext(Instant.now()));
    }

    private Mono<Void> triggerNext(Instant from) {
        Instant nextInstant = rule.nextAfter(from);
        Duration duration = Duration.between(Instant.now(), from);
        return Mono.delay(duration)
                .flatMap(t -> {
                    TaskContext context = new TaskContext(
                            nextInstant,
                            Instant.now(),
                            Map.of("reactive", "reactive-task")
                    );

                    return task.execute(context);
                })
                .then(triggerNext(nextInstant)); // recursion
    }
}
