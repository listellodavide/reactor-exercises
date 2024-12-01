package com.adiwave.reactorexercises.sec02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class MonoSubscribe {

    private static final Logger log = LoggerFactory.getLogger(MonoSubscribe.class);

    public static void main(String[] args) {
        var mono = Mono.just(10)
                .map( i -> i * 20);
                //.map(i -> i / 0);


        mono.subscribe(
                i -> log.info("received: {}", i),
                err -> log.error("process failed with exception {}", err),
                () -> log.info("completed"),
                subscription -> subscription.request(1)
        );

    }
}
