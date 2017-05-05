package datastructure;

/**
 * Created by safe on 2017/3/1.
 */
public class Pow {
    public static long pow(long x, int n){
        if (n==0)
            return 1;
        if (n==1)
            return x;
        if(n % 2==0)
            return pow(x*x,n/2);
        else
            return pow(x*x,n/2)*x;

    }
    public static void main(String[] args) {
        System.out.println(pow(2,10));
        System.out.println(pow(2,5));
        System.out.println(pow(2,11));
    }
}
