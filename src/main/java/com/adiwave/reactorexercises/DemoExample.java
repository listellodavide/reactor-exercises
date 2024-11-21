package com.adiwave.reactorexercises;

import com.adiwave.reactorexercises.publisher.PublisherImpl;
import com.adiwave.reactorexercises.publisher.SubscriptionImpl;
import com.adiwave.reactorexercises.subscriber.SubscriberImpl;

import java.time.Duration;

public class DemoExample {

    /*
        Basic Rules of Reactive Programming
        1. publisher does not produce data unless subscriber requests for it
        2. publisher will produce only <= subscriber requested items. Publisher can also produce 0 items !
        3. subscriber can cancel the subscription, producer should stop at that moment as subscriber is no interested in consuming data.
        4. producer can send the error signal to indicate something is wrong.
     */
    private static final PublisherImpl publisher = new PublisherImpl();
    private static final SubscriberImpl subscriber = new SubscriberImpl();
    public static void main(String[] args) throws InterruptedException {
        publisher.subscribe(subscriber);
        Thread.sleep(Duration.ofSeconds(3));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));

        // somethingIsWrong();

        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(3));
    }

    public static void somethingIsWrong() {
        subscriber.getSubscription().request(13);
        subscriber.getSubscription().cancel();
    }
}
