package com.adiwave.reactorexercises.sec02;

import com.adiwave.reactorexercises.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class CaseFromRunnable {

    private static final Logger log = LoggerFactory.getLogger(CaseFromRunnable.class);

    public static void main(String[] args) {
        getProductName(2)
                .subscribe(Util.subscriber("product processor"));
    }


    private static Mono<String> getProductName(int productId) {
        if(productId == 1) {
            return Mono.fromSupplier( () -> Util.getFaker().commerce().productName());
        }
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    private static void notifyBusiness(int productId) {
        log.info("Notify business on unavailable product {}", productId);
    }
}
