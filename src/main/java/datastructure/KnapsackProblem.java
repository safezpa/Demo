package datastructure;

/**
 * 01背包问题描述
 * http://blog.csdn.net/insistgogo/article/details/8579597
 * 已知:有一个容量为V的背包和N件物品，第i件物品的重量是weight[i]，收益是cost[i]。
 * 限制:每种物品只有一件，可以选择放或者不放
 * 问题:在不超过背包容量的情况下，最多能获得多少价值或收益
 * 相似问题:在恰好装满背包的情况下，最多能获得多少价值或收益
 */

/**
 *
 * 基本思路
 * 01背包的特点:每种物品只有一件，可以选择放或者不放
 *
 * 子问题定义状态
 * f[i][v] : 前i件物品放到一个容量为v的背包中可以获得最大价值
 *
 * 状态转移方程
 * f[i][v] = max(f[i - 1][v],f[i - 1][c - weight[i]] + cost[i])
 *
 * 分析
 * 考虑我们的子问题，将前i件物品放到容量为v的背包中，若我们只考虑第i件物品时，它有两种选择，放或者不放。
 * 1) 如果第i件物品不放入背包中，那么问题就转换为：
 * 将前i - 1件物品放到容量为v的背包中，
 * 带来的收益f[i - 1][c]
 * 2) 如果第i件物品能放入背包中，那么问题就转换为：
 * 将前i - 1件物品放到容量为v - weight[i]的背包中，
 * 带来的收益f[i - 1][c - weight[i]] + value[i]
 *
 *
 */
public class KnapsackProblem {
     private static int[][] zeroOnePack(int N,int[] weight, int[] value,int C){
         int[][] f=new int[N+1][C+1];
         for (int i = 1; i <=N ; i++) {
             for (int c = 1; c <=C ; c++) {
                 f[i][c]=f[i-1][c];
                 if (c>weight[i])
                 f[i][c]=Integer.max(f[i-1][c],f[i-1][c-weight[i]]+value[i]);
             }
         }

         return f;
     }

    /**
     * 优化空间复杂度
     * @param N
     * @param weight
     * @param value
     * @param C
     * @return
     */
    private static int[] knapsack2(int N,int[] weight, int[] value,int C){
        int[] f=new int[C+1];
        for (int i = 1; i <=N ; i++) {
            //而逆序枚举背包容量：
            // 背包中的物品至多装一次，使价值最大，当然不会不超过背包容量


            //逆序枚举物品  优化下界 bound=max{V-sum{weight[i..n]},weight[i]}
            for (int c = C; c >=weight[i] ; c--) {
                    f[c]=Integer.max(f[c],f[c-weight[i]]+value[i]);
                System.out.print(f[c]+"\t");
            }
            System.out.println();
        }

        return f;
    }

    //顺序枚举容量是完全背包问题最简捷的解决方案
    private static int[] completePack(int N,int[] weight, int[] value,int C){
        int[] f=new int[C+1];
        for (int i = 1; i <=N ; i++) {
            for (int c = 1; c <=C ; c++) {
                if (c>=weight[i])
                f[c]=Integer.max(f[c],f[c-weight[i]]+value[i]);
                System.out.print(f[c]+"\t");
            }
            System.out.println();
        }

        return f;
    }


    /**
     * 二维背包问题 逆序
     *
     * @param
     */

    //
    private static int[][][] knapsack2ins(int N,int[] weight, int[] value,int[] d2,int L,int C){
        int[][][] f=new int[N+1][C+1][L+1];

        for (int i = 1; i <=N ; i++) {
            for (int c = 1; c <=C ; c++) {
                for (int l = 1; l<=L ; l++) {
                    f[i][i][c]=f[i-1][c][l];
                    if (c>weight[i] && l>d2[i])
                    f[i][c][l]=Integer.max(f[i-1][c][l],f[i-1][c-weight[i]][l-d2[i]]+value[i]);
                }
            }
            System.out.println();
        }

        return f;
    }


    /**
     * 二维背包问题 顺序三维数组
     *
     * @param
     */

    //
    private static int[][] knapsack2dec(int N,int[] weight, int[] value,int[] d2,int L,int C){
        int[][] f=new int[C+1][L+1];

        for (int i = 1; i <=N ; i++) {
            for (int c = C; c >=1 ; c--) {
                for (int l = L; l>d2[i] ; l--) {
                    f[c][l]=Integer.max(f[c][l],f[c-1][l-d2[i]]+value[i]);
                }
            }
            System.out.println();
        }

        return f;
    }
    public static void main(String[] args) {
        int[] weight={0,2,2,6,5,4};
        int[] value={0,6,3,5,4,6};
        int N=5;
        int C=10;
        int[][] f=zeroOnePack(N,weight,value,C);
        for (int i = N; i >=1 ; i--) {
            for (int c = 1; c <=C ; c++) {
                System.out.print(f[i][c]+"\t");
            }
            System.out.println();
        }
        System.out.println("=================================");
        int[] f2=completePack(N,weight,value,C);
        System.out.println("+++++++++++++完全背包++++++++++++++++++++");
        for (int c = C; c >=0 ; c--) {
            System.out.print(f2[c]+"\t");
        }
    }
}










/**
 * 求最优解的背包问题时，有两种问法：
 * 1)在不超过背包容量的情况下，最多能获得多少价值
 * 2)在恰好装满背包的情况下，最多能获得多少价值
 *
 * 1)恰好装满背包的情况：使用二维数组f[i][v]存储中间状态，其中第一维表示物品，第二维表示背包容量
 * 初始化时，除了f[i][0] = 0（第一列）外，其他全为负无穷。
 * 使用一维数组f[v]存储中间状态，维表示背包容量
 * 初始化时，除了f[0] = 0，其他全为负无穷。
 * 2)不需要把背包装满，只需要收益最大
 * 使用二维数组f[i][v]存储中间状态，其中第一维表示物品，第二维表示背包容量
 * 初始化时，除了f[i][0] = 0（第一列）外，其他全为负无穷。
 * 使用一维数组f[v]存储中间状态，v表示背包容量
 */


/**
 * 下面给出O(log amount)时间处理一件多重背包中物品的过程，其中amount表示物品的数量：
 * procedure MultiplePack(cost,weight,amount)
 * if cost*amount>=V
 * CompletePack(cost,weight)
 * return
 * integer k=1
 * while k<amount
 * ZeroOnePack(k*cost,k*weight)
 * amount=amount-k
 * k=k*2
 * ZeroOnePack(amount*cost,amount*weight)
 *
 */

