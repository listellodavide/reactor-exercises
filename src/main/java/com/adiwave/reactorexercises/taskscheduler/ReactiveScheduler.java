package com.adiwave.reactorexercises.taskscheduler;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

public class ReactiveScheduler {

    private final List<ScheduledReactiveTask> taskList;

    public ReactiveScheduler(List<ScheduledReactiveTask> taskList) {
        this.taskList = taskList;
    }

    public Flux<Void> start() {
        return Flux.merge(
                taskList.stream()
                        .map(ScheduledReactiveTask::schedule)
                        .collect(Collectors.toList())
        );
    }

}
