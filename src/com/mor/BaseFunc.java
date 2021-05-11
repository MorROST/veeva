package com.mor;

public class BaseFunc {

    public Boolean isPyramid(int num) {
        return isEdgeDigitsAreEqual(num) && isTherePeak(num);
    }

    private Boolean isTherePeak(int num) {
        int peak = findingThePeak(num);
        boolean peakFound = false;
        int prev = -1;
        while (num != 0) {
            int digit = num % 10;
            if (!peakFound && digit == peak) {
                peakFound = true;
                prev = digit;
                num /= 10;
                continue;
            }
            if (digit == prev) {
                System.out.println("The digit and the previous digit are equal");
                return false;
            }
            if (peakFound) {
                if (digit > prev) {
                    System.out.println("The digit AFTER the 'peak' digit must to be smaller");
                    return false;
                }
            } else {
                if (digit < prev) {
                    System.out.println("The digit BEFORE the 'peak' digit must to be smaller");
                    return false;
                }
            }
            prev = digit;
            num /= 10;
        }
        return true;
    }

    private int findingThePeak(int num) {
        int max = 0;
        while (num != 0) {
            int digit = num % 10;
            max = Math.max(digit, max);
            num /= 10;
        }
        return max;
    }


    private Boolean isEdgeDigitsAreEqual(int num) {
        if (num > 9) {
            int lastDigit = num % 10;
            while (num >= 10) {   //Find the first digit
                num /= 10;
            }
            return lastDigit == num;
        }else{
            return false;
        }
    }
}
