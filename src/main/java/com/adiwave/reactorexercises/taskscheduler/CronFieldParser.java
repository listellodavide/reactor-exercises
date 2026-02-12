package com.adiwave.reactorexercises.taskscheduler;

import java.util.BitSet;

public class CronFieldParser {

    static CronField parse(String field, int min, int max) {
        BitSet bits = new BitSet(max + 1);

        if(field.equals("*")) {
            bits.set(min, max + 1);
        }
        else if (field.startsWith("*/")) {
            int step = Integer.parseInt(field.substring(2));
            for(int i = min; i <= max; i += step) {
                bits.set(i);

            }
        } else {
            int value = Integer.parseInt(field);
            bits.set(value);
        }

        return new CronField(bits, min, max);
    }
}
