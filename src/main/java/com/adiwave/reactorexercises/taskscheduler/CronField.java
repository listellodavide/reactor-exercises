package com.adiwave.reactorexercises.taskscheduler;

import java.util.BitSet;

public class CronField {

    private final BitSet allowed;
    private final int min;
    private final int max;

    CronField(BitSet allowed, int min, int max) {
        this.allowed = allowed;
        this.min = min;
        this.max = max;
    }

    boolean matches(int value) {
        return allowed.get(value) && value >= min && value <= max;
    }

    int nextOrSome(int value){
        int next = allowed.nextSetBit(value);
        if(next >= 0) return next;
        return allowed.nextSetBit(min);
    }
}
