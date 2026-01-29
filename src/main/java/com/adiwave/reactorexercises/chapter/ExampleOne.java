package com.adiwave.reactorexercises.chapter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

public class ExampleOne {

    public static void main(String[] args) {
        ExampleOne exampleOne = new ExampleOne();
        exampleOne.getNames()
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .subscribe(
                        name -> {
                            System.out.println(name);
                        }
                );
        exampleOne.unreliableOperation()
                .subscribe(System.out::println, System.err::println); // return an error or success message
    }

    Flux<String> getNames() {
        return Flux.fromIterable(List.of("Alice", "Bob", "Davide", "Adrian", "Matthew"));
    }

    public Mono<String> unreliableOperation() {
        return Mono.fromCallable(() -> {
                if (Math.random() < 0.8) {
                    throw new RuntimeException("Simulated error");
                }
                return "Success";
            })
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                    .filter(throwable -> throwable instanceof RuntimeException)
                    .doBeforeRetry(retrySignal -> {
                        System.out.println("Retrying due to: " + retrySignal.failure().getMessage());
                    })
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()));
    }

}
