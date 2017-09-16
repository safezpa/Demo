package mytest.didi;

import java.util.Scanner;

public class Main5 {

    public static void main(String[] args)   {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        int[] uglyN=new int[n];
        System.out.println(geNthUgl(n,uglyN));
    }

   public static int min3(int a,int b,int c){
        int min =(a<b) ? a:b;
        min = (min<c) ? min:c;
        return min;
    }

   public static int geNthUgl(int n,int[] uglyN) {
        if(n <= 0)
            return 0;
        uglyN[0] = 1;
        int index= 1;
        int i2 = 0; //2的倍数的丑数位置
        int i3 = 0; //3的倍数
        int i5 = 0;
        while(index < n){
            /**
             * 我们把已有的每一个丑数乘以2,3和5，
             * 能得到第一个大于M的结果M3和M5。
             * 那么下一个丑数应该是M2、M3和M5三个数的最小者
             */
            int min = min3(2*uglyN[i2],3*uglyN[i3],5*uglyN[i5]);
            uglyN[index] = min;
            while(2*uglyN[i2] <= uglyN[index])   ++i2;
            while(3*uglyN[i3] <= uglyN[index])   ++i3;
            while(5*uglyN[i5] <= uglyN[index])   ++i5;
            ++index;
        }
        int out = uglyN[n-1];
        return out;
    }


}



