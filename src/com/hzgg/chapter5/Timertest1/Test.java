package com.hzgg.chapter5.Timertest1;

import java.util.TimerTask;

public class Test {
    private static int[] arr = new int[10];
    private int size=0;
    public void add( int item){
      arr[++size] = item;
      fixUp(size);
    }
    private void fixUp(int k) {
        while (k > 1) {
            int j = k >> 1;
            if (arr[j] <= arr[k])
                break;
            int  tmp = arr[j];  arr[j] = arr[k]; arr[k] = tmp;
            k = j;
            for( int a: arr){
                System.out.print(a + "");
            }
            System.out.println("");
        }
    }
    private void fixDown(int k) {
        int j;
        while ((j = k << 1) <= size && j > 0) {
            if (j < size && arr[j] > arr[j+1])
                j++; // j indexes smallest kid
            if (arr[k] <= arr[j])
                break;
            int tmp = arr[j];  arr[j] = arr[k]; arr[k] = tmp;
            k = j;
            for( int a: arr){
                System.out.print(a + "");
            }
            System.out.println("");
        }
        System.out.println("8888888888888");
    }
    void removeMin() {
        arr[1] = arr[size];
        arr[size--] = 0;  // Drop extra reference to prevent memory leak
        fixDown(1);
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.add(2);
        test.add(4);
        test.add(1);
        test.add(8);
        test.add(3);
        test.add(7);
        test.add(10);
        test.add(5);
        System.out.println("===========================");
        for(int a : arr){
            System.out.println(a);
        }
        System.out.println("===========================");
        test.removeMin();
//        for(int a : arr){
//            System.out.println(a);
//        }
    }
}
