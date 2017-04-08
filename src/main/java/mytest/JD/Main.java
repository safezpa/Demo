package mytest.JD;

import java.util.Scanner;

/**
 * Created by safe on 2017-04-07.
 */
public class Main {

    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] p=new int[n];

        //得到做错的概率
        for (int i = 0; i <n ; i++) {
            p[i]=100-in.nextInt();
        }

        //至少做对m道题
         int m=(int)Math.round(n*0.6)+1;

        //至多做错n-m道题
        int wr=n-m;

        int sum=1;
        //    //做错一道
        for (int i = 1; i <=wr ; i++) {
            //做错一道
            //1-(p[])
            //做错两道
            sum=sum*p[i];

        }

        //select(int[] P,个数，) 求乘积

        System.out.println();
    }


}
