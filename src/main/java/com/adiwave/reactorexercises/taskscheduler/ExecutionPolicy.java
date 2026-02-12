package com.adiwave.reactorexercises.taskscheduler;

import java.util.concurrent.atomic.AtomicBoolean;

public interface ExecutionPolicy {

    boolean canStart(AtomicBoolean running);
}
