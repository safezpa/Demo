package mytest.netease;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //每天房租
        int x= in.nextInt();
        //已经有的水果
        int f= in.nextInt();
        //已经有的钱
        int d= in.nextInt();
        //每个水果单价
        int p= in.nextInt();
        in.close();


        //换算成钱
        int total=f*p+d;
        //每天花费
        int cost=p+x;
        //先吃水果+钱 过几天
        int n1=f;
        //剩余的钱
        int d1=d-f*x;
        if (d1>0){
            int n2=d1/cost;
            int n0=total/cost;
            if (n0>n2) System.out.println(n0);
            System.out.println(n1+n2);
        }else{
            System.out.println(n1);
        }
    }

}
