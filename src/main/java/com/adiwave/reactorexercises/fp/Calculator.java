package com.adiwave.reactorexercises.fp;

import java.util.List;

public class Calculator {


    public int applyMinNegative(List<Integer> input) {
        return input.stream()
                .filter( i -> i < 0) // optional filter
                .min(Integer::compare)
                //.orElseThrow(() -> new IllegalArgumentException("Input list cannot be empty"));
                .orElse(0);
    }

    public int applyMaxPositive(List<Integer> input) {
        return input.stream()
                .filter( i -> i > 0)
                .max(Integer::compare)
                //.orElseThrow(() -> new IllegalArgumentException("Input list cannot be empty"));
                .orElse(0);
    }


    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        List<Integer> input1 = List.of(-2,-45,-100, -423422, 2, 34,70,99999,2399282, 123);
        System.out.println(String.format("Max number on list input1 is: %d",calculator.applyMaxPositive(input1)));
        System.out.println(String.format("Min number on list input1 is: %d",calculator.applyMinNegative(input1)));
    }
}
