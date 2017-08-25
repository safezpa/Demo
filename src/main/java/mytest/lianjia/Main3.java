package mytest.lianjia;

import java.util.Scanner;

public class Main3 {
    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);

        int[] hi=new int[10];
        for (int i = 0; i <10 ; i++) {
            hi[i]=in.nextInt();
        }
        int h=in.nextInt();

        int n=0;
        for (int i = 0; i <10 ; i++) {
            if (h+30>=hi[i]) n++;
        }

        System.out.println(n);

    }



}



