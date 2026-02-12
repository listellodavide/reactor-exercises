package com.adiwave.reactorexercises.fp;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class RomanConverter {

    // Problem Exercise: https://leetcode.com/problems/roman-to-integer/

    // As input is
    // Regex for valid Roman numerals (1-3999)
    private static final Pattern ROMAN_PATTERN =
            Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

    private int charToInteger(char c) {
        return switch(c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            //default -> 0;
            default -> throw new IllegalArgumentException("Unexpected character: " + c);
        };
    }
           //           current=1  next=10
           //  I   X    1           10      10 > 1 ? 10 - 1 = 9
           //  I   V
    private int romanToIntCalculation(String s) {
        return IntStream.range(0, s.length())  // for (int i = 0 ; i < s.length(); i++)
                .map(i -> {
                    int current = charToInteger(s.charAt(i));
                    // int next = (i+1) < s.length() ? chartToInteger(s.charAt(i+1)) : 0;
                    // if(current < next) return -current; else return current;
                    // BinaryFunction(  )
                    int next = i + 1 < s.length() ? charToInteger(s.charAt(i + 1)) : 0;
                    return current < next ? -current : current;
                })
                .sum();

    }

    // first validate the input string and then convert to decimal
    public int romanToInt(String s) {
        // 1. Validation Guard (Functional approach using Predicate)
        if (s == null || s.isEmpty() || !ROMAN_PATTERN.matcher(s.toUpperCase()).matches()) {
            throw new NumberFormatException("Invalid Roman numeral: " + s);
        }
        return romanToIntCalculation(s.toUpperCase());
    }

    public static void main(String[] args) {
        RomanConverter converter = new RomanConverter();
        String romanNum1 = "MCMXCIV";
        int decimalNum1 = converter.romanToInt(romanNum1);
        System.out.println("Roman1: " + romanNum1 + " -> Decimal1: " + decimalNum1);

        // the logic should detect that this input is invalid
        String romanNum2 = "IIX"; // VIII
        try {
            int decimalNum2 = converter.romanToInt(romanNum2);
            System.out.println("Roman2: " + romanNum2 + " -> Decimal2: " + decimalNum2);
        }catch(NumberFormatException e) {
            System.out.println("Invalid Roman numeral: " + romanNum2);
        }

        String romanNum3 = "LXXXVIII";
        int decimalNum3 = converter.romanToInt(romanNum3);
        System.out.println("Roman3: " + romanNum3 + " -> Decimal3: " + decimalNum3);

        String romanNum4 = "CCC";
        int decimalNum4 = converter.romanToInt(romanNum4);
        System.out.println("Roman4: " + romanNum4 + " -> Decimal4: " + decimalNum4);

        String romanNum5 = "MMMDCCCLXXXVIII"; // maximum valid roman number
        int decimalNum5 = converter.romanToInt(romanNum5);
        System.out.println("Roman5: " + romanNum5 + " -> Decimal5: " + decimalNum5);

        String romanNum6 = "DCCC";
        int decimalNum6 = converter.romanToInt(romanNum6);
        System.out.println("Roman6: " + romanNum6 + " -> Decimal6: " + decimalNum6);

        // the logic should detect that this input is invalid
        String romanNum7 = "CCM";
        try {
            int decimalNum7 = converter.romanToInt(romanNum7);
            System.out.println("Roman7: " + romanNum7 + " -> Decimal7: " + decimalNum7);
        }catch(NumberFormatException e) {
            System.out.println("Invalid Roman numeral: " + romanNum7);
        }

    }

}
