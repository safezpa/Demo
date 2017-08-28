package mytest.qihu;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] a=new int[n+1];
        for (int i = 1; i <=n ; i++) {
           a[i]=in.nextInt();
        }
        in.close();

        int[][] ans=new int[9][9];

        for (int i = 1; i <=n ; i++) {
            int k=0;
            for (int j = 1; j <=i ; j++) {
                if (a[i]<a[j]) k++;
            }
            if (i==n) {
                System.out.print(k);
            }else {
                System.out.print(k+" ");
            }
        }


    }


}



