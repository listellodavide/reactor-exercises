package com.adiwave.reactorexercises.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriberImpl implements Subscriber<String> {

    private static final Logger log = LoggerFactory.getLogger(SubscriberImpl.class);
    private Subscription subscription;

    public Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
       this.subscription = subscription;
        log.info("onSubscribe called");
    }

    @Override
    public void onNext(String s) {
        log.info("received: {}", s);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError called", throwable);
    }

    @Override
    public void onComplete() {
        log.info("onComplete called");
    }
}
