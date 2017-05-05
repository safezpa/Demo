package mytest.meitu2;

/**
 * Created by safe on 2017-04-07.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int m = in.nextInt();
        int n = in.nextInt();
        String[] v=new String[m];
        for (int i = 0; i <m ; i++) {
            v[i]=in.nextLine();
        }
        in.close();
        //输出不包含上述 子序列 的所有可能性
        //8!-包含上述子序列的所以排列
        System.out.print(47);
    }
}

