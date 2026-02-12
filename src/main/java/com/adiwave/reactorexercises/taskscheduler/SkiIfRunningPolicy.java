package com.adiwave.reactorexercises.taskscheduler;

import java.util.concurrent.atomic.AtomicBoolean;

public class SkiIfRunningPolicy implements ExecutionPolicy {

    @Override
    public boolean canStart(AtomicBoolean running) {
        return running.compareAndSet(false, true);
    }
}
