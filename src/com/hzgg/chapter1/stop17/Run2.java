package com.hzgg.chapter1.stop17;

public class Run2 {
    public static void main(String[] args) {
        Thread.currentThread().interrupt();
        System.out.println("threand 1 ==" + Thread.interrupted());
        System.out.println("thread 2 ===" + Thread.interrupted());
    }
}
