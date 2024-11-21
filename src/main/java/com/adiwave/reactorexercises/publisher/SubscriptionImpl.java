package com.adiwave.reactorexercises.publisher;

import net.datafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);

    Subscriber<? super String> subscriber;

    private static final int MAX_ITEMS = 10;
    private final Faker faker;
    private boolean isCanceled = false;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = new Faker();
    }

    @Override
    public void request(long requested) {
        if(isCanceled) return;
        log.info("requested {} items.. processing..", requested);
        if(requested > MAX_ITEMS) {
            this.isCanceled = true;
            this.subscriber.onError(new RuntimeException("requested " + requested + " items exceeds " + MAX_ITEMS));
            return;
        }
        for(int i = 0; i < requested && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(faker.name().fullName());
        }

        if(count >= MAX_ITEMS) {
            log.info("No more data !");
            this.subscriber.onComplete();
            this.isCanceled = true;
        }
    }

    @Override
    public void cancel() {
        log.info("Subscriber has canceled");
        this.isCanceled = true;
    }
}
