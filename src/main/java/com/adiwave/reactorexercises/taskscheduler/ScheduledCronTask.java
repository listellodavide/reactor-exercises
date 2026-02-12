package com.adiwave.reactorexercises.taskscheduler;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

public class ScheduledCronTask {

    private final CronExpression cron;
    private final ReactiveTask task;
    private final ZoneId zone;

    public ScheduledCronTask(CronExpression cron, ReactiveTask task, ZoneId zone) {
        this.cron = cron;
        this.task = task;
        this.zone = zone;
    }

    public Mono<Void> start() {
        return Mono.defer( () -> scheduleNext(ZonedDateTime.now(zone)));
    }

    private Mono<Void> scheduleNext(ZonedDateTime from) {
        ZonedDateTime next = cron.nextAfter(from);
        Instant nextInstant = next.toInstant();
        Duration duration = Duration.between(Instant.now(), nextInstant);

        if(duration.isNegative()) {
            duration = Duration.ZERO;
        }

        return Mono.delay(duration)
                .flatMap(t -> {
                    TaskContext context = new TaskContext(
                            nextInstant,
                            Instant.now(),
                            Map.of("cron", cron.toString())
                    );

                    return task.execute(context);
                })
                .onErrorResume( e-> {
                    System.err.println("Task execution error: " + e.getMessage());
                    return Mono.empty();
                })
                .then(Mono.defer(() -> scheduleNext(next)));

    }


    public static void main(String[] args) {

        WebClient webClient = WebClient.create();

        ReactiveTask httpTask = new DefaultReactiveTask(
                "fetch-users",
                ctx -> {
                    System.out.println("Executing task at " + Instant.now());
                    return webClient.get()
                            .uri("https://jsonplaceholder.typicode.com/todos/1") // Changed to absolute URI for testing
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(String.class)
                            .doOnNext(body -> System.out.println("Response: " + body.substring(0, Math.min(body.length(), 50)) + "..."))
                            .then();
                },
                new SkiIfRunningPolicy()
        );

        // Changed cron to run every 2 seconds: "*/2 * * * * *"
        ScheduledCronTask scheduledCronTask = new ScheduledCronTask(
                new DefaultCronExpression("*/2 * * * * *"),
                httpTask,
                ZoneId.of("Europe/Rome"));

        scheduledCronTask.start().subscribe();
        
        try {
            // Keep main thread alive long enough to see execution
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
