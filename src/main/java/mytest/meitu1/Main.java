package mytest.meitu1;

/**
 * Created by safe on 2017-04-07.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        in.close();

        int max = Math.max(n,m);
        int i = 0, j = 0;

        int[][] a = new int[max][max];

        //位置
        int  p= 1;
        //判断7或L型增加
        boolean  q = true;
        //左上坐标
        int x0 = 0;
        int y0 = 0;

        //右下坐标
        int xn = n;
        int ym = m;


        while (true) {
            //7字型
            if (q == true) {
                for (; j < ym; j++) {
                    a[i][j] = p++;
                }

                //下一行
                j--;
                i++;
                ym--;


                for (; i < xn; i++) {
                    a[i][j] = p++;
                }

                i--;
                j--;
                xn--;
                q = false;
            //L型
            } else {
                for (; j >= y0; j--) {
                    a[i][j] = p++;
                }

                j++;
                i--;
                y0++;
                for (; i >= x0 + 1; i--) {
                    a[i][j] = p++;
                }

                i++;
                j++;
                x0++;
                q = true;

            }

            if (p > m * n) {
                break;
            }

        }


        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                System.out.print(a[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}

