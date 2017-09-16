package mytest.meitu2;


import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] a = new int[n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            if (a[i] == 0) k++;
        }
        in.close();
        if (k % 2 == 0) System.out.println("Bob");
        else System.out.println("Alice");
    }
}
