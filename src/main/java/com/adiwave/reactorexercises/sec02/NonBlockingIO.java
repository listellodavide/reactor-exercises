package com.adiwave.reactorexercises.sec02;

import com.adiwave.reactorexercises.client.ExternalServiceClient;
import com.adiwave.reactorexercises.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonBlockingIO {

    private static final Logger logger = LoggerFactory.getLogger(NonBlockingIO.class);

    public static void main(String[] args) {
        ExternalServiceClient externalServiceClient = new ExternalServiceClient();
        externalServiceClient.getProductName(15)
                .subscribe(Util.subscriber(""));

        Util.sleepSeconds(2);
    }
}
