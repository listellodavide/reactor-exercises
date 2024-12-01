package com.adiwave.reactorexercises.common;

import net.datafaker.Faker;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Util {

    public static final Faker faker = new Faker();

    public static <T> Subscriber<T> subscriber(Subscriber<T> subscriber) {
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        var mono = Mono.just(10);

        mono.subscribe(subscriber("subscriber1"));
        mono.subscribe(subscriber("subscriber2"));
    }

    public static Faker getFaker() {
        return faker;
    }
}
