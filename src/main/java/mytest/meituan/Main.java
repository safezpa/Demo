package mytest.meituan;

/**
 * Created by safe on 2017-04-07.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in=new Scanner (System.in);
        int n=in.nextInt();
        int[] a=new int[n+1];
        int[] sum=new int[n+1];
        int[] rem=new int[n+1];
        int[] cnt=new int[n+1];

        for(int i=1;i<=n;i++){
            a[i]=in.nextInt();
        }
        int k=in.nextInt();
        System.out.println("====================================");
        in.close();
        sum[0]=0;
        rem[0]=0;
        a[0]=0;
        rem[0]=0;
        cnt[0]=1;
        int longest=0;
        for (int i = 1; i <=n ; i++) {
            sum[i]=sum[i-1]+a[i];
            rem[i]=sum[i]%k;
            cnt[rem[i]]++;

            if (cnt[rem[i]]>=2 ) longest=i;
        }

        for(int i=0;i<=n;i++){
            System.out.print(a[i]+" ");
        }
        System.out.print("\n");
        for(int i=0;i<=n;i++){
            System.out.print(sum[i]+" ");
        }
        System.out.print("\n");
        for(int i=0;i<=n;i++){
            System.out.print(rem[i]+" ");
        }
        System.out.print("\n");
        for (int i = 0; i <k ; i++) {
            System.out.print(cnt[i]+" ");
        }
        System.out.print("\n");
        int num=0;
        for (int i = 0; i <k ; i++) {
            num+=cnt[i]*(cnt[i]-1)/2;
        }
        System.out.println(k+"倍数区间个数"+num);
        //记住余数的个数及起始位置
        System.out.print(longest);
    }
}

