package com.adiwave.reactorexercises.sec02;

import com.adiwave.reactorexercises.client.ExternalServiceClient;
import com.adiwave.reactorexercises.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonBlockingIO {

    private static final Logger logger = LoggerFactory.getLogger(NonBlockingIO.class);

    public static void main(String[] args) {
        ExternalServiceClient externalServiceClient = new ExternalServiceClient();

        for(int i = 1; i <= 15; i++) {
            externalServiceClient.getProductName(i)
                    .subscribe(Util.subscriber(""));

        }

        FileServiceImpl fileService = new FileServiceImpl();
        fileService.write("new-file.txt", "Hello World !! \r\n My Friend Dexter !")
                .subscribe(Util.subscriber("write"));
        fileService.read("new-file.txt")
                .subscribe(Util.subscriber("read"));
        fileService.delete("new-file.txt")
                .subscribe(Util.subscriber("delete"));

        Util.sleepSeconds(2);
    }
}
