package mytest.JD;

/**
 * Created by safe on 2017-04-07.
 */
public class Cmn {

    public static double cmn(int m,int n) {
        if (n==0 || m==n)
            return 1;
        return   cmn(m-1,n-1)+cmn(m-1,n);
    }
    public static void main(String[] args) {
        System.out.println(cmn(5,2));

    }
}
