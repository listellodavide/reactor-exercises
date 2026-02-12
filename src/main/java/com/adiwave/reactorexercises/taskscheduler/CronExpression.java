package com.adiwave.reactorexercises.taskscheduler;

import java.time.ZonedDateTime;

public interface CronExpression {

    ZonedDateTime nextAfter(ZonedDateTime from);
}
