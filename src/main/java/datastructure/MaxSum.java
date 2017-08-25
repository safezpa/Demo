package datastructure;

import java.util.Scanner;

/**
 * dynamic programming is a method for solving a complex problem
 * by breaking it down into a collection of simpler subproblems.
 *
 * 动态规划是通过拆分问题，定义问题状态和状态之间的关系，
 * 使得问题能够以递推（或者说分治）的方式去解决。
 *
 * 如何拆分问题，才是动态规划的核心。
 * 而拆分问题，靠的就是
 *
 * 状态的定义
 *
 * 和
 *
 * 状态转移方程
 *
 * 的定义。
 *
 */

/**
 * 在上面的数字三角形中寻找一条从顶部到底边的路径，
 * 使得路径上所经过的数字之和最大。路径上的每一步都只能往左下或 右下走。
 * 只需要求出这个最大和即可，不必给出具体路径。
 * 三角形的行数大于1小于等于100，数字为 0 - 99
 * 输入格式：
 * 5      //表示三角形的行数    接下来输入三角形
 * 7
 * 3   8
 * 8   1   0
 * 2   7   4   4
 * 4   5   2   6   5
 *
 * 要求输出最大和
 *
 */

/**
 *
 5

 7

 3   8

 8   1   0

 2   7   4   4

 4   5   2   6   5
 */


public class MaxSum {

    /**
     * 递归型
     * @param i
     * @param j
     * @param D
     * @param n
     * @return
     */
    private static int maxSum(int i, int j,int[][] D,int n){
        if(i==n)
            return D[i][j];
        int x = maxSum(i+1,j,D,n);
        int y = maxSum(i+1,j+1,D,n);
        return Integer.max(x,y)+D[i][j];
    }

    /**
     * 记忆递归型的动态规划程序
     * @param i
     * @param j
     * @return
     */
   private static int maxSumMemo(int i, int j, int[][] D, int[][] maxSum,int n){
        if( maxSum[i][j] != -1 )
            return maxSum[i][j];
        if(i==n)
            maxSum[i][j] = D[i][j];
        else{
            int x = maxSumMemo(i+1,j,D,maxSum,n);
            int y = maxSumMemo(i+1,j+1,D,maxSum,n);
            maxSum[i][j] = Integer.max(x,y)+ D[i][j];
        }
        return maxSum[i][j];
    }

    /**
     * 递推型动态规划程序
     * @param D
     * @param maxSum
     * @param n
     * @return
     */
    private static int maxSumMemoDownTop(int[][] D, int[][] maxSum,int n){

        //最后一行赋初值
        for( int i = 1;i <= n; ++ i )
            maxSum[n][i] = D[n][i];

        for( int i = n-1; i>= 1;  --i )
            for( int j = 1; j <= i; ++j )
                maxSum[i][j] = Integer.max(maxSum[i+1][j],maxSum[i+1][j+1]) + D[i][j];
        return maxSum[1][1];
    }


    /**
     * 递推型动态规划程序 节省空间
     * @param D
     * @param maxSum
     * @param n
     * @return
     */
    private static int maxSumMemoDownTop(int[][] D, int[] maxSum,int n){
        //最后一行赋初值
        for( int i = 1;i <= n; ++ i )
            maxSum[i] = D[n][i];

        for( int i = n-1; i>= 1;  --i )
            for( int j = 1; j <= i; ++j )
                maxSum[j] = Integer.max(maxSum[j],maxSum[j+1]) + D[i][j];
        return maxSum[1];
    }

    public static void main(String[] args) {
        int i,j,n;
        Scanner in=new Scanner(System.in);
        n=in.nextInt();
        int[][] D=new int[n+1][n+1];
        int[][] maxSum=new int[n+1][n+1];

        int[] maxSum1=new int[n+1];
        for(i=1;i<=n;i++)
            for(j=1;j<=i;j++){
                D[i][j]=in.nextInt();
                maxSum[i][j]=-1;
            }


        long t0=System.nanoTime();
        System.out.println(maxSum(1,1,D,n));
        long t1=System.nanoTime();
        System.out.println(t1-t0);

        System.out.println(maxSumMemo(1,1,D,maxSum,n));
        long t2=System.nanoTime();
        System.out.println(t2-t1);

        System.out.println(maxSumMemoDownTop(D,maxSum,n));
        long t3=System.nanoTime();
        System.out.println(t3-t2);



        System.out.println(maxSumMemoDownTop(D,maxSum1,n));
        long t4=System.nanoTime();
        System.out.println(t4-t3);

    }



}
