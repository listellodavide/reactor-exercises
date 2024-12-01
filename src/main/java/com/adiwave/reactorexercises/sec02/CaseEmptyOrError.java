package com.adiwave.reactorexercises.sec02;

import com.adiwave.reactorexercises.common.Util;
import reactor.core.publisher.Mono;

public class CaseEmptyOrError {

    public static void main(String[] args) {

        //getUsername(1).subscribe(Util.subscriber("subscriber 3"));
        getUsername(3)
                .subscribe(
                        s -> System.out.println(s),
                        error -> {}
                );
    }

    private static Mono<String> getUsername(int userId) {
        return switch(userId) {
            case 1 -> Mono.just("sam");
            case 2 -> Mono.empty(); // null / optional equivalent
            default -> Mono.error(new RuntimeException("Invalid user id: " + userId));
        };
    }
}
