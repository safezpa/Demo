package offer;

/**
 * Created by safe on 2017-07-06.
 */
public class Fibonacci {
    public static int fibonacci(int n) {
        if (n<=1){
            return 1;
        }else {
            return fibonacci(n-1)+fibonacci(n-2);
        }


    }
    public static int fibonacci2(int n) {
            if (n<=0) return 0;
            if (n==1) return 1;
        int fn0=0;
        int fn1=1;
        int fn=0;
        for (int i=2;i<=n;++i){
            fn=fn0+fn1;
            fn0=fn1;
            fn1=fn;
        }
        return fn;
    }
    public static void main(String[] args) {
       // System.out.println(fibonacci2(4));
        System.out.println(2<<4);
    }
}
