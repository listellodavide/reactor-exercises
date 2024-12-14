package com.adiwave.reactorexercises.sec02;

import com.adiwave.reactorexercises.client.ExternalServiceClient;
import com.adiwave.reactorexercises.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;


public class NonBlockingIO {

    private static final Logger logger = LoggerFactory.getLogger(NonBlockingIO.class);

    public static void main(String[] args) {
        // service not available !!
        // TODO: Pay Idea Subscription 1 year on 15.01.2025
        /* ExternalServiceClient externalServiceClient = new ExternalServiceClient();

        for(int i = 1; i <= 15; i++) {
            externalServiceClient.getProductName(i)
                    .subscribe(Util.subscriber(""));

        }
        */
        FileServiceImpl fileService = new FileServiceImpl();
        fileService.write("new-file.txt", "Hello World !! \r\n My Friend Dexter !")
                .subscribe(Util.subscriber("write"));
        fileService.read("new-file.txt")
                .subscribe(Util.subscriber("read"));
        fileService.delete("new-file.txt")
                .subscribe(Util.subscriber("delete"));


        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

        flux.filter(i -> i % 2 == 0)
                .subscribe(Util.subscriber("sub1"));
        flux.filter(i -> i > 7)
            .map(i -> "a"+i+"b")
                .subscribe(Util.subscriber("sub2"));
        flux.filter(i -> i % 3 == 0)
                .subscribe(Util.subscriber("sub3"));
        flux.filter( i -> i % 5 == 0)
                .subscribe(Util.subscriber("sub4"));


        Util.sleepSeconds(2);
    }
}
