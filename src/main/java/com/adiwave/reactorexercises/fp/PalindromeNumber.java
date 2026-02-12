package com.adiwave.reactorexercises.fp;

import java.util.stream.IntStream;

import static java.lang.Integer.sum;

public class PalindromeNumber {

    public boolean isPalindrome(int x) {
        String s = Integer.valueOf(x).toString();
        return IntStream.range(0, s.length() / 2)
                .map( i -> s.charAt(i) == s.charAt(s.length() - i - 1) ? 1 : 0)
                .sum() == s.length() / 2;
    }

    public static void main(String[] args) {
        PalindromeNumber palindromeNumber = new PalindromeNumber();
        System.out.println(palindromeNumber.isPalindrome(1)); // 0 == 0
        System.out.println(palindromeNumber.isPalindrome(1121)); // 1 != 2
    }
}
