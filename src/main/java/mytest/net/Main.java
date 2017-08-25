package mytest.net;

import java.util.Scanner;

/**
 *
 * 差值最小就是说两部分的和最接近，而且各部分的和与总和的一半也是最接近的。
 * 假设用sum1表示第一部分的和，sum2表示第二部分的和，
 * SUM表示所有数的和，那么sum1+sum2=SUM。假设sum1<sum2 那么SUM/2-sum1 = sum2-SUM/2;
 * 所以我们就有目标了， 使得sum1<=SUM/2的条件下尽可能的大。也就是说从n个数中选出某些数，
 * 使得这些数的和尽可能的接近或者等于所有数的和的一般。这其实就是简单的背包问题了：
 * 背包容量是SUM/2. 每个物体的体积是数的大小，然后尽可能的装满背包。
 * dp方程：f[i][V] = max(f[i-1][V-v[i]]+v[i], f[i-1][V] )
 * f[i][V]表示用前i个物体装容量为V的背包能够装下的最大值，
 * f[i-1][V-v[i]]+v[i]表示第i个物体装进背包的情况，
 * f[i-1][V]表示第i件物品不装进背包的情况。
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n= in.nextInt();
        int[] task=new int[n];
        int sum=0;


        for (int i = 0; i <n ; i++) {
            task[i]=in.nextInt()/1024;
            sum=sum+task[i];
        }
        in.close();
        int[] dp=new int[1000000];
        //int[][] dp=new int[n+1][sum/2+1];
/*        for (int i = 0; i <=n ; i++) {
            for (int v = 0; v <=sum/2 ; v++) {

                dp[i][v]=i == 0 ?0:dp[i-1][v];
                if (i>0 && v>task[i-1])
                    dp[i][v]=Math.max(dp[i-1][v],dp[i-1][v-task[i-1]]+task[i-1]);
            }

          t+=Math.max(dp[n][n/2],sum-dp[n][n/2]*1024);
        }*/

        for(int i = 0 ; i < n ; i ++)
            for(int j = sum/2 ; j >= task[i] ; --j)
                dp[j] = Math.max(dp[j],dp[j-task[i]]+task[i]);//dp[j]表示在容量为j的情况可存放的重量



        System.out.println((sum-dp[sum/2])*1024);



    }

}
