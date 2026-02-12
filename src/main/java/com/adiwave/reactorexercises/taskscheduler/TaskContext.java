package com.adiwave.reactorexercises.taskscheduler;

import java.time.Instant;
import java.util.Map;

public class TaskContext {

    private final Instant scheduledTime;
    private final Instant triggerTime;
    private final Map<String, String> metadata;

    public TaskContext(
            Instant scheduledTime,
            Instant triggerTime,
            Map<String, String> metadata
    ) {
        this.scheduledTime = scheduledTime;
        this.triggerTime = triggerTime;
        this.metadata = metadata;
    }

    public Instant getScheduledTime() { return this.scheduledTime; }
    public Instant getTriggerTime() { return this.triggerTime; }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
