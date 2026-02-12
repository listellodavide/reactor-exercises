package com.adiwave.reactorexercises.taskscheduler;

import java.time.Instant;

public interface ScheduledRule {
    Instant nextAfter(Instant from);
}
