package datastructure;

/**
 * Created by safe on 2017/3/1.
 */
public class Gcd {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static long gcd(long m, long n){
        while (n!=0){
            long rem=m%n;
            m=n;
            n=rem;
        }
        return m;
    }

    public static void main(String[] args) {
        System.out.println("gcd（1989，1590）="+gcd(1989,1590));
    }
}
