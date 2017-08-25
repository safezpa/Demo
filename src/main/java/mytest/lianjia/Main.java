package mytest.vivo;

import java.util.Scanner;

/**
 * Created by safe on 2017-04-11.
 * <p>
 * 贪心策略：要使乘积做大，尽可能地将指定的n(n>4)拆分成从2开始的连续的自然数的和，
 * 如果最后有剩余的数，将这个剩余的数在优先考虑后面项的情况下平均分给前面的各项。
 * 例：
 * n=10,先拆分为：10=2+3+4+1，最后一项为1，比4小，将其分配给前面的一项，得到10=2+3+5，
 * 所以最大的乘积为2*3*5=30.
 * n=20,拆分为：20=2+3+4+5+6，刚刚好，最大乘积为2*3*4*5*6=720.
 * n=26，拆分为：26=2+3+4+5+6+6，
 * 因为最后一项为6，不比最后的第二项大，
 * 所以将其平均分给前面的项，优先考虑后面的项，
 * 即前面的4项各分到1，第5项，分到2，
 * 最后是26=3+4+5+6+8，所以最大的乘积为3*4*5*6*8=2880.
 * <p>
 * <p>
 * <p>
 * 基本算法描述如下：
 * 1． 拆分过程
 * 拆分的数a先取2;
 * 当n>a 时做
 * begin
 * 选择a作为一项；
 * a增加1；
 * n减少a;
 * end;
 * <p>
 * 如果n>0，那么将n从最后一项开始平均分给各项；
 * 如果n还大于0，再从最后一项开始分一次；
 */

/**
 * Created by safe on 2017-04-11.
 */
public class Main {
    public static void main(String[] args)   {
        int[]  a=new int[1000];
        int n;
        int out=1;
        Scanner in = new Scanner(System.in);
        n=in.nextInt();
        int sum=0;
        int len=0;
        int left=0;
        for(int i=2;i<=n;i++)
        {
            a[len++]=i;
            sum=sum+i;
            if(sum>n)
            {
                sum=sum-i;
                len--;
                left=n-sum;
                break;
            }
        }
        boolean flag=true;
        int j=len-1;
        while (flag){
            a[j]++;
            j--;
            if(j<0) j=len-1;
            left--;
            if (left==0) flag=false;
        }

        for(int i=0;i<=len-1;i++){
            System.out.println(a[i]);
            out=out*a[i];
        }

        System.out.println(out);


    }

}



