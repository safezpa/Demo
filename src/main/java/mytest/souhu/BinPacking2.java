package mytest.souhu;

import java.util.Scanner;

/**
 * @author Gavenyeah
 * @date Time: 2016年4月23日下午10:40:35
 * @des:
 */
// 装箱问题：有一个箱子容量为v(正整数，o≤v≤20000)，同时有n个物品(o≤n≤30)，每个物品有一个体积
// (正整数)。要求从m个物品中，任取若千个装入箱内，使箱子的剩余空间为最小。
public class BinPacking2 {
    private static int volume[] = null;
    private  static int max_V = 0;

    public static void main(String[] args) {
        getInput();
        binPacking();
    }

    // f(i,j)表示前i件物品在给定j体积时所能达到的最大值
    private static void binPacking() {
        int[][] maxSumVolume = new int[volume.length][max_V + 1];
        for (int i = 1; i < volume.length; i++) {
            for (int j = 1; j <= max_V; j++) {
                if (j >= volume[i]) {
                    maxSumVolume[i][j] = Math.max(maxSumVolume[i - 1][j],
                            maxSumVolume[i - 1][j - volume[i]] + volume[i]);
                }
            }
        }
        int minSurplus = max_V - maxSumVolume[volume.length - 1][max_V];
        System.out.println("最小剩余：" + minSurplus);
    }

     private static void getInput() {// 从键盘输入数据
        Scanner cin = new Scanner(System.in);
        System.out.print("请输入箱子容量：");
        max_V = cin.nextInt();
        System.out.print("请输入物品数：");
        int m = cin.nextInt();
        volume = new int[m + 1];
        System.out.print("依次输入每件物品的重量：");
        for (int i = 1; i < m + 1; i++) {
            volume[i] = cin.nextInt();
        }
        cin.close();
    }
}
