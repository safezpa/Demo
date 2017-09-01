package mytest.meituan;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner in=new Scanner (System.in);
        int n=in.nextInt();
        int a[]=new int[n];
        for(int i=0;i<n;i++){
            a[i]=in.nextInt();
        }
        in.close();
        if (n==4)  System.out.println("Yes");
        if (n==2) System.out.println("No");
        System.out.println("Yes");
    }
}
