package mytest.netease2;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n= in.nextInt();
        for (int i = 0; i <n ; i++) {
            int k=in.nextInt();
            int[] a=new int[k];
            int count=0;
            for (int j = 0; j <k ; j++) {
                a[j]=in.nextInt();
                if (a[j]%2==0) count++;
            }
            if (count>Math.floor(k/2)){
                System.out.println("Yes");
            }
            else {
                System.out.println("No");
            }
        }

    }

}
