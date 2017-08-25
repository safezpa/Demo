package com.example;


public class DemoApplicationTests {
    public double power(double base, int exponent) {

        return Math.pow(base,exponent);
    }

    public static void main(String[] args) {
    System.out.println(DemoApplicationTests.class.getResource("/").getPath());
}
}