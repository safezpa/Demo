package mytest.lianjia;

import java.util.Scanner;

public class Main2 {
    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] a=new int[n+1];
        for (int i = 1; i <=n ; i++) {
           a[i]=in.nextInt();
        }

        int m=in.nextInt();
        int[] q=new int[m+1];
        for (int i = 1; i <=m ; i++) {
            q[i]=in.nextInt();
            System.out.println(find(a,q[i]));
        }
        in.close();

    }

    private static int find(int[] a,int q ){
        int tmp=0;
        for (int i = 1; i <=a.length; i++) {
            tmp+=a[i];
            if (tmp>=q) return i;
        }
        return tmp;
    }

}



